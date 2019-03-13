package Socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.BufferedReader;

public class SocketForServer {
	public static final int LISTEN_PORT = 9453;
	private JTextArea textArea;
	private String data;
	private String status;
	private JTextField textFieldSyncData;
	
    public void listenRequest()
    {
        ServerSocket serverSocket = null;
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try
        {
            serverSocket = new ServerSocket( LISTEN_PORT );
            textArea.append("Server listening requests on port : " + LISTEN_PORT);
            Socket socket = serverSocket.accept();
            threadExecutor.execute( new RequestThread( socket ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( threadExecutor != null )
                threadExecutor.shutdown();
            if ( serverSocket != null )
                try
                {
                    serverSocket.close();
                    
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
        }
        return;
    }
    
    SocketForServer(JTextField textFieldSyncData, JTextArea textArea, String data, String status)
    {
    	this.textFieldSyncData = textFieldSyncData;
    	this.textArea = textArea;
    	this.data = data;
    	this.status = status;
    	this.listenRequest();
    }
    
    private void doChatRoom(DataInputStream input, DataOutputStream output)
    {
    	try
    	{
	    	String inputData;
	    	do
	    	{
	    		inputData = input.readUTF();
	    		textArea.append(inputData);
	    	}while(inputData.contains("/stop"));
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /**
     * 處理Client端的Request執行續。
     *
     * @version
     */
    class RequestThread implements Runnable
    {
        private Socket clientSocket;
        
        public RequestThread( Socket clientSocket )
        {
            this.clientSocket = clientSocket;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            textArea.append("\n" + "Connection from "  + clientSocket.getRemoteSocketAddress() + "\n" + "Ready For Syncing");
            DataInputStream input = null;
            DataOutputStream output = null;
            try
            {

            	if(status.equals("From"))
            	{
            		output = new DataOutputStream( this.clientSocket.getOutputStream() );
                    output.writeUTF( data );
                    output.flush();
            	}
            	
            	if(status.equals("To"))
            	{
            		input = new DataInputStream( this.clientSocket.getInputStream() );
            		textFieldSyncData.setText(input.readUTF());
            	}
                
            	if(status.equals("Server"))
            	{
            		input = new DataInputStream( this.clientSocket.getInputStream() );
            		BufferedReader dis;
        			dis = new BufferedReader(new InputStreamReader(input));
        			data = dis.readLine();
        			while(data != null)
        			{
        				textArea.append("\n" + data);
        				data = dis.readLine();
        			}
            	}
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            finally 
            {
                try
                {
                	textArea.append("\n" + "Sync Done.");
                    if ( input != null )
                        input.close();
                    if ( output != null )
                        output.close();
                    if ( this.clientSocket != null && !this.clientSocket.isClosed() )
                    {
                        this.clientSocket.close();
                    }
                    
                    
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
         }
    }
}
