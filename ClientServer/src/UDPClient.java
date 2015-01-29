
// Simple UDP File Transfer Client


import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;


public class UDPClient

{

    

    private DatagramSocket clientSocket;
    
    private InetAddress IPAddress;
    
    private int portNumber;

    public UDPClient(String IPAddress, int portNumber) throws SocketException, UnknownHostException
    {
    	clientSocket = new DatagramSocket();
    	this.IPAddress = InetAddress.getByName(IPAddress);
    	this.portNumber = portNumber;
    }
    
    public void SendFile(String filePath) throws IOException
    {
    	
        FileInputStream file = null;

        byte[] buffer = new byte[500];     

        try
        {
        	int read = 0;
            file = new FileInputStream(filePath);
            while ( ( read = file.read(buffer)) != -1 )
            {	
                System.out.println("Data : "+buffer.toString());
                SendPacket(buffer);
                Arrays.fill( buffer, (byte)0);
            }

        }
        finally
        {
        	SendPacket(StopBuffer(buffer));
            clientSocket.close();
            CloseFile(file);
        }

    }

	private byte[] StopBuffer(byte[] buffer) 
	{
			Arrays.fill(buffer, (byte)-1);
		 return buffer;
	}

	private void CloseFile(FileInputStream file) 
	{
		  try
          {
              if ( file != null )
                  file.close();
          }
          catch ( Exception e)
          {
              System.out.println(e);    
          }
		
	}

	private void SendPacket(byte[] buffer) throws IOException 
	{
		 DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, IPAddress, portNumber);

         clientSocket.send(sendPacket);
	}

}

/*

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

      clientSocket.receive(receivePacket);

      String modifiedSentence = new String(receivePacket.getData());

      System.out.println("FROM SERVER:" + modifiedSentence);

 */

