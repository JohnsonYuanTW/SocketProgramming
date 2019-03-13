package Socket;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class SocketForClient {
	private static int port = 9453;
	private String host = "140.115.81.224";
	private StringBuffer s = new StringBuffer();
	Socket socket = null;
	
	SocketForClient(JTextField textFieldSyncData, JTextArea textArea, String data, String status)
	{
		try
        {
		socket = new Socket( this.host, this.port );
        DataInputStream input = null;
        DataOutputStream output = null;
	        try
	        {
	        	if(status.equals("From"))
	        	{
	        		input = new DataInputStream( socket.getInputStream() );
	        		textFieldSyncData.setText(input.readUTF());
	        		textArea.setText("Data retrieved.");
	        	}
	        	if(status.equals("To"))
	        	{
	        		output = new DataOutputStream( socket.getOutputStream() );
	        		output.writeUTF( textFieldSyncData.getText() );
                    output.flush();
                    textArea.setText("Sync Done.");
	        	}
	        	if(status.equals("Server"))
	        	{
	        		output = new DataOutputStream( socket.getOutputStream() );
	        		output.writeUTF( data );
                    output.flush();
                    textArea.append("Sync Done.");
	        	}
	        }
	        catch ( IOException e )
	        {
	        	
	        }
	        finally 
	        {
	            if ( input != null )
	                input.close();
	            if ( output != null )
	                output.close();
	        }
	    }
	    catch ( IOException e )
	    {
	        e.printStackTrace();
	        textArea.append("The server is not connected.\nPlease check server status.\n");
	    }
	    finally
	    {
	    	try
            {
	    		if ( socket != null )
		            socket.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
	    }
	}
}
