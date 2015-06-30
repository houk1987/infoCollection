package org.infoCollection.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.infoCollection.service.TyCollection;

public class CollectionFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton start;
	private JButton cancel;
	
	private JLabel countlJLabel;
	
	private TyCollection tyCollection;
	
	private Work work;
	
	private Timer timer;
	
	private void init(){
		
		tyCollection = new TyCollection();
		work = new Work();
		timer = new Timer(1, this);
		setLayout(new BorderLayout());
        
		
		countlJLabel = new JLabel("数量：");
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		start = createButton("开始");
		cancel = createButton("取消");
		
		panel.add(start);
		
		panel.add(cancel);
		
		add(countlJLabel,BorderLayout.CENTER);
		add(panel,BorderLayout.SOUTH);
		
		setSize(500, 500);
		setLocationRelativeTo(null);
		
	}
	
	private JButton createButton(String text){
		JButton button = new JButton(text);
		button.addActionListener(this);
		return button;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)){
			work.execute();
			timer.start();
		}else if(e.getSource().equals(cancel)){
			tyCollection.canecle();
			work.cancel(true);
			timer.stop();
		}else {
			countlJLabel.setText("数量："+tyCollection.getCount());
		}
	}
	
	class Work extends SwingWorker<Boolean, Void>{

		@Override
		protected Boolean doInBackground() throws Exception {
			tyCollection.colletion("/list-333-1.shtml");
			return true;
		}
	}
	
	
	public static void main(String args[]){
		CollectionFrame clCollectionFrame = new CollectionFrame();
		clCollectionFrame.init();
		clCollectionFrame.setVisible(true);
	}

}
