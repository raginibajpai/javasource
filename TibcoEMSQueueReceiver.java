package au.gov.nsw.sydneytrains.remiims.service;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
 
import com.tibco.tibjms.TibjmsQueueConnectionFactory;
 
public class TibcoEMSQueueReceiver {
 
	public static void main(String args[]) {
 
			String serverUrl = "XXXXXX";
			String userName = "icfms";
			String password = "XXXXX";		
		
			String queueName = "XXXXX";
			
 
		try {
			
			//URL=tcp://awseaidev.rail.nsw.gov.au:3064;clientID=null;Properties={com.tibco.tibjms.multicast.enabled=true}]
 
			System.out.println("Start listening for incoming JMS message on " + serverUrl + "...");
 
			QueueConnectionFactory factory = new TibjmsQueueConnectionFactory(serverUrl);
			QueueConnection connection = factory.createQueueConnection(userName, password);
			QueueSession session = connection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
 
			// Use createQueue() to enable receiving from dynamic queues.
			Queue receiverQueue = session.createQueue(queueName);
			QueueReceiver receiver = session.createReceiver(receiverQueue);
 
			connection.start();
 
			/* read queue messages */
			while (true) {
				TextMessage message = (TextMessage) receiver.receive();
				if (message == null)
					break;
 
				System.out.println("Received message: " + message.getText());
			}
 
			connection.close();
 
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
 
}