package inficraft.infiblocks.bioxx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetLoginHandler;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;

//public class PacketHandler implements IPacketHandler, IConnectionHandler {
public class PacketHandler{

	public static final byte Packet_Init_Block_Client = 0;
	public static final byte Packet_Init_Block_Server = 1;
	public static final byte Packet_Keypress_Server = 2;
	public static final byte Packet_Init_World_Client = 3;
	public static final byte Packet_Data_Client = 4;
	public static final byte Packet_Data_Server = 5;
	public static final byte Packet_Player_Status = 6;

	/*@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) 
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(clientHandler.getPlayer().username, manager));
		TerraFirmaCraft.proxy.onClientLogin();
	}


	@Override
	public void playerLoggedIn(Player p, NetHandler netHandler, INetworkManager manager)
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(((EntityPlayer)p).username, manager));

		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		EntityPlayerMP player = (EntityPlayerMP)p;
		TFC_PlayerServer playerserver = (TFC_PlayerServer) player.getServerPlayerBase("TFC Player Server");
		World world= player.worldObj;

		if(!world.isRemote)
		{
			playerserver.getFoodStatsTFC().resetTimers();
			try
			{
				dos.writeByte(Packet_Init_World_Client);
				dos.writeLong(world.getSeed());
				dos.writeLong(TFC_Time.dayLength);
				dos.writeInt(TFC_Time.daysInYear);
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Packet250CustomPayload pkt=new Packet250CustomPayload();
			pkt.channel="TerraFirmaCraft";
			pkt.data=bos.toByteArray();
			pkt.length=bos.size();
			pkt.isChunkDataPacket=false;

			((NetServerHandler)netHandler).sendPacketToPlayer(pkt);
		}
	}

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player p)
	{
		DataInputStream dis=new DataInputStream(new ByteArrayInputStream(packet.data));
		
		byte type = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		try {
			type = dis.readByte();

			EntityPlayer player = (EntityPlayer)p;
			TFC_PlayerClient playerClient = null;
			TFC_PlayerServer playerServer = null;;
			World world= player.worldObj;
			if(world.isRemote)
			{
				playerClient = (TFC_PlayerClient)((EntityPlayerSP)player).getPlayerBase("TFC Player Client");
			}
			else
			{	
				playerServer = (TFC_PlayerServer)((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server");
			}

			if(type == Packet_Init_Block_Client)//Client recieves the init packet from the server and assigns the data
			{

				try
				{
				x = dis.readInt();
				y = dis.readInt();
				z = dis.readInt();

				if(world != null)
				{
					NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleInitPacket(dis);
				}
				}catch(Exception e)
				{
					System.out.println("PacketHandler error in Packet Type: " + type + ", "+x + ", "+y + ", "+z);
				}
			}
			else if(type == Packet_Init_Block_Server)//Server builds the init packet and sends it to the client.
			{

				x = dis.readInt();
				y = dis.readInt();
				z = dis.readInt();

				ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
				DataOutputStream dos=new DataOutputStream(bos);
				NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
				if(te != null)
				{

					dos.writeByte(Packet_Init_Block_Client);
					dos.writeInt(x);
					dos.writeInt(y);
					dos.writeInt(z);
					te.createInitPacket(dos);

					Packet250CustomPayload pkt=new Packet250CustomPayload();
					pkt.channel="TerraFirmaCraft";
					pkt.data=bos.toByteArray();
					pkt.length=bos.size();
					pkt.isChunkDataPacket=false;

					TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP) player, pkt);
				}
			}
			else if(type == Packet_Keypress_Server)
			{
				byte change;
				if(keyTimer + 1 < TFC_Time.getTotalTicks())
				{
					keyTimer = TFC_Time.getTotalTicks();
					try 
					{
						change = dis.readByte();
						if(change == 0)//ChiselMode
						{
							PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

							if(pi!=null) pi.switchChiselMode();
						}
						else if(change == 1)//Chisel Depth Increase
						{
							PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

							if(pi!=null) pi.switchIncreaseDetailZoom();
						}
						else if(change == 2)//Chisel Depth Increase
						{
							PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

							if(pi!=null) pi.switchDecreaseDetailZoom();
						}
					} 
					catch (IOException e) 
					{
						return;
					} 
				}
			}
			else if(type == Packet_Init_World_Client)
			{
				if(world.isRemote)
				{
					long seed = 0;
					try 
					{
						seed = dis.readLong();
						TFC_Time.dayLength = dis.readLong();
						TFC_Time.daysInYear = dis.readInt();
						playerClient.getFoodStatsTFC().foodLevel = dis.readFloat();
						playerClient.getFoodStatsTFC().waterLevel = dis.readFloat();

					} catch (IOException e) 
					{
						// IMPOSSIBLE?
					}
					TFC_Core.SetupWorld(world, seed);
				}
			}
			else if(type == Packet_Data_Client)
			{
				if(world.isRemote)
				{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleDataPacket(dis);
				}
			}
			else if(type == Packet_Data_Server)
			{
				if(!world.isRemote)
				{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleDataPacketServer(dis);
				}
			}
			else if (type == Packet_Player_Status)
			{
				if(world.isRemote)
				{
					try 
					{
						playerClient.getFoodStatsTFC().foodLevel = dis.readFloat();
						playerClient.getFoodStatsTFC().waterLevel = dis.readFloat();

					} catch (IOException e) {}
				}
			}
		} catch (Exception e) 
		{
			return;
		}
	}
	static long keyTimer = 0;

	@SideOnly(Side.CLIENT)
	public static void sendKeyPress(int type) //0 = chiselmode
	{
		if (!ModLoader.getMinecraftInstance().theWorld.isRemote) return;
		else 
		{
			try
			{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				Packet250CustomPayload pkt = new Packet250CustomPayload();

				dos.writeByte(Packet_Keypress_Server);
				dos.writeByte(type);
				dos.close();

				pkt.channel="TerraFirmaCraft";
				pkt.data=bos.toByteArray();
				pkt.length=bos.size();
				pkt.isChunkDataPacket=false;

				TerraFirmaCraft.proxy.sendCustomPacket(pkt);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed(INetworkManager manager) 
	{
		PlayerInfo PI = new PlayerInfo("", manager);
		for(int i = 0; i < PlayerManagerTFC.getInstance().Players.size() && PI != null; i++)
		{
			if(PlayerManagerTFC.getInstance().Players.get(i).networkManager == manager)
			{
				System.out.println("PlayerManager Successfully removed player " + PlayerManagerTFC.getInstance().Players.get(i).Name);
				PlayerManagerTFC.getInstance().Players.remove(i);
			}  
		}
		
//		if(TerraFirmaCraft.proxy.isRemote())
//			manager.closeConnections();
//		else
//			manager.serverShutdown();
	}*/
}
