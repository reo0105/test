import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RentalVideoShop {
	private JTextField rentalmemIdField;
	private JTextField rentalVideoTitleField;
	private JTextField planField;
	private JTextField videoTitleField;
	private JTextField videoNumInShopField;
	private JTextField videoNumInRentalField;
	private JTextField videoIdField;
	private JTextField videoNameField;
	private JTextField memberIdField;
	private JTextField memberNameField;
	private JTextField memberTitleField;
	private JTextField memberYearField;
	private JTextField memberMonthField;
	private JTextField memberDayField;
	private DefaultListModel<Video> videoListModel;
	private DefaultListModel<Member> memberListModel;
	private DefaultListModel<String> lendTitleListModel;
	private DefaultListModel<String> videoTitleListModel;
	private DefaultListModel<String> videoNameListModel;
	private DefaultListModel<String> memberNameListModel;
	private DefaultListModel<String> memberTitleListModel;
	private JList<String> lendTitleList;
	private JList<String> videoTitleList;
	private JList<String> videoNameList;
	private JList<String> memberNameList;
	private JList<String> memberTitleList;
	
	JButton rentalRegButton;
	JButton rentalDelButton;
	JButton rentalQuitButton;
	JButton videoShowButton;
	JButton videoClearButton;
	JButton videoRegButton;
	JButton videoDelButton;
	JButton videoNameShowButton;
	JButton videoNameClearButton;
	JButton memberNameShowButton;
	JButton memberNameClearButton;
	JButton memberRegButton;
	JButton memberDelButton;
	JButton memberTitleRegButton;
	JButton memberTitleDelButton;
	JButton memberTitleShowButton;
	JButton memberTitleClearButton;
	
	private Application app = new Application();
	/* Listener in Rental */
	class RentalRegButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int id = Integer.valueOf(rentalmemIdField.getText());
				String title = rentalVideoTitleField.getText();
				Calendar date = Calendar.getInstance();
				int plan = Integer.valueOf(planField.getText());
				app.rental(id, title, date, plan, memberListModel, videoListModel, lendTitleListModel);
			} catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(rentalRegButton, "Please Correct Input Format!", "Format Error", JOptionPane.ERROR_MESSAGE);
			} catch(VideoException ex) {
				JOptionPane.showMessageDialog(rentalRegButton, ex.getMessage(), "Title Error", JOptionPane.ERROR_MESSAGE);
			} catch(MemberException ex) {
				JOptionPane.showMessageDialog(rentalRegButton, ex.getMessage(), "ID Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class RentalDelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = lendTitleList.getSelectedIndex();
			if (index != -1) {
				int id = Integer.valueOf(rentalmemIdField.getText());
				app.removeLendVideoRental(id, index, memberListModel, videoListModel, lendTitleListModel);
			} else {
				JOptionPane.showMessageDialog(rentalDelButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class QuitButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	/* Listener in Video  */
	class VideoShowButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (videoTitleList.getSelectedIndex() != -1) {
				String title = (videoListModel.get(videoTitleList.getSelectedIndex())).getTitle();
				int inRental = (videoListModel.get(videoTitleList.getSelectedIndex())).getLend();
				int inShop = (videoListModel.get(videoTitleList.getSelectedIndex())).getInventory();
				videoTitleField.setText(title);
				videoNumInRentalField.setText(String.valueOf(inRental));
				videoNumInShopField.setText(String.valueOf(inShop));
				app.showLendMember(title, videoListModel, videoNameListModel, memberListModel);
			} else {
				JOptionPane.showMessageDialog(videoShowButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class VideoClearButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			videoTitleList.clearSelection();
		}
	}

	class VideoRegButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int index = videoTitleList.getSelectedIndex();
				if (index != -1) {
					String title = videoTitleField.getText();
					int inRental = Integer.valueOf(videoNumInRentalField.getText());
					int inShop = Integer.valueOf(videoNumInShopField.getText());
					app.revVideo(index, title, inRental, inShop, memberListModel, videoListModel, videoTitleListModel);
				} else {
					String title = videoTitleField.getText();
					int inShop = Integer.valueOf(videoNumInShopField.getText());
					app.addVideo(title, inShop, videoListModel, videoTitleListModel);
				}
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(videoRegButton, "Please Correct Input Format!", "Format Error", JOptionPane.ERROR_MESSAGE);
			} catch(VideoTitleException ex) {
				JOptionPane.showMessageDialog(videoRegButton, ex.getMessage(), "Title Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class VideoDelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int index = videoTitleList.getSelectedIndex();
				if (index != -1) {
					app.removeVideo(index, videoListModel, videoTitleListModel);
				} else {
					JOptionPane.showMessageDialog(videoDelButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (DelException ex) {
				JOptionPane.showMessageDialog(videoDelButton, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class VideoNameShowButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (videoNameList.getSelectedIndex() != -1) {
				int index = videoNameList.getSelectedIndex();
				String title = videoTitleField.getText();
				int id = app.getMemberIdInRental(index, title, videoListModel);
				String name = videoNameListModel.get(index);
				videoIdField.setText(String.valueOf(id));
				videoNameField.setText(name);
			} else {
				JOptionPane.showMessageDialog(videoNameShowButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class VideoNameClearButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			videoNameList.clearSelection();
		}
	}
	
	/* Listener in member */
	class MemberNameShowButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (memberNameList.getSelectedIndex() != -1) {
				int memId = (memberListModel.get(memberNameList.getSelectedIndex())).getId();
				String memName = (memberListModel.get(memberNameList.getSelectedIndex())).getName();
				memberIdField.setText(String.valueOf(memId));
				memberNameField.setText(memName);
				app.showLendVideo(memId, memberListModel, memberTitleListModel);
			} else {
				JOptionPane.showMessageDialog(memberNameShowButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class MemberNameClearButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			memberNameList.clearSelection();
		}
	}

	class MemberRegButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int index = memberNameList.getSelectedIndex();
				if (index != -1) {
					int id = Integer.valueOf(memberIdField.getText());
					String name = memberNameField.getText();
					app.revMember(index, id, name, memberListModel, videoListModel, memberNameListModel);
				} else {
					int id = Integer.valueOf(memberIdField.getText());
					String name = memberNameField.getText();
					app.addMember(id, name, memberListModel, memberNameListModel);
				}
			} catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(memberRegButton, "Please Correct Input Format!", "Format Error", JOptionPane.ERROR_MESSAGE);
			} catch(MemberIdException ex) {
				JOptionPane.showMessageDialog(memberRegButton, ex.getMessage(), "ID Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class MemberDelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int index = memberNameList.getSelectedIndex();
				if (index != -1) {
					app.removeMember(index, memberListModel, memberNameListModel);
				} else {
					JOptionPane.showMessageDialog(memberDelButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (DelException ex) {
				JOptionPane.showMessageDialog(memberDelButton, ex.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class MemberTitleShowButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
			if (memberTitleList.getSelectedIndex() != -1) {
				int index = memberNameList.getSelectedIndex();
				int id = Integer.valueOf(memberIdField.getText());
				if (memberListModel.get(index).getId() != id) {
					throw new Exception();
				}
				String title = memberTitleListModel.get(memberTitleList.getSelectedIndex());
				int year = app.getRentalLimitYear(index, title, memberListModel);
				int month = app.getRentalLimitMonth(index, title, memberListModel);
				int day = app.getRentalLimitDay(index, title, memberListModel);
				memberTitleField.setText(title);
				memberYearField.setText(String.valueOf(year));
				memberMonthField.setText(String.valueOf(month));
				memberDayField.setText(String.valueOf(day));
			} else {
				JOptionPane.showMessageDialog(memberTitleShowButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(memberTitleShowButton, "Please Push Left Show Button!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class MemberTitleClearButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			memberTitleList.clearSelection();
		}
	}
	
	class MemberTitleRegButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int index = memberTitleList.getSelectedIndex();
				if (index != -1) {
					int id = Integer.valueOf(memberIdField.getText());
					String title = memberTitleField.getText();
					int year = Integer.valueOf(memberYearField.getText());
					int month = Integer.valueOf(memberMonthField.getText());
					int day = Integer.valueOf(memberDayField.getText());
					app.revTitle(index, id, title, year, month, day, memberListModel, videoListModel, memberTitleListModel);
				} else {
					JOptionPane.showMessageDialog(memberTitleRegButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(memberTitleRegButton, "Please Correct Input Format!", "Format Error", JOptionPane.ERROR_MESSAGE);
			} catch(VideoException ex) {
				JOptionPane.showMessageDialog(memberTitleRegButton, ex.getMessage(), "Title Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class MemberTitleDelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int indexTitle = memberTitleList.getSelectedIndex();
				if (indexTitle != -1) {
					int id = Integer.valueOf(memberIdField.getText());
					int indexMember = memberNameList.getSelectedIndex();
					app.removeLendVideo(id, indexMember, indexTitle, memberListModel, videoListModel, memberTitleListModel);
				} else {
					JOptionPane.showMessageDialog(memberTitleDelButton, "None Selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(memberTitleDelButton, "Please Push Show Button!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	class TabChangeAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			rentalmemIdField.setText("");;
			rentalVideoTitleField.setText("");
			planField.setText("");
			videoTitleField.setText("");
			videoNumInShopField.setText("");
			videoNumInRentalField.setText("");
			videoIdField.setText("");
			videoNameField.setText("");
			memberIdField.setText("");
			memberNameField.setText("");
			memberTitleField.setText("");
			memberYearField.setText("");
			memberMonthField.setText("");
			memberDayField.setText("");
			app.RefreshPanels(videoListModel, memberListModel, lendTitleListModel, videoTitleListModel, videoNameListModel, memberNameListModel, memberTitleListModel);
		}
	}

	public Component createComponents() {
		memberListModel = new DefaultListModel<Member>();
		videoListModel = new DefaultListModel<Video>();
		
		/* ****** Rental Tab ****** */
		/* Field in Rental*/
		rentalmemIdField = new JTextField();
		rentalVideoTitleField = new JTextField();
		planField = new JTextField();

		/* List in Rental Tab */
		lendTitleListModel = new DefaultListModel<String>();
		lendTitleList = new JList<String>(lendTitleListModel);
		lendTitleList.setVisibleRowCount(10);
		lendTitleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rentalScrollPane = new JScrollPane(lendTitleList);
		rentalScrollPane.createVerticalScrollBar();
		rentalScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		/* Register Button */
		rentalRegButton = new JButton("Register");
		RentalRegButtonAction rentalRegButtonListener = new RentalRegButtonAction();
		rentalRegButton.addActionListener( rentalRegButtonListener );
		rentalRegButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		/* Delete Button */
		rentalDelButton = new JButton("Delete");
		RentalDelButtonAction rentalDelButtonListener = new RentalDelButtonAction();
		rentalDelButton.addActionListener( rentalDelButtonListener );

		/* Quit Button */
		rentalQuitButton = new JButton("Quit");
		QuitButtonAction rentalquitButtonListener = new QuitButtonAction();
		rentalQuitButton.addActionListener( rentalquitButtonListener );

		/* Panels in Rental */
		JPanel subPanerentalDelQuit = new JPanel();
		subPanerentalDelQuit.setLayout(new GridLayout(1, 0));
		subPanerentalDelQuit.add(rentalDelButton);
		subPanerentalDelQuit.add(Box.createRigidArea(new Dimension(30, 10)));
		subPanerentalDelQuit.add(rentalQuitButton);
		
		JPanel subPaneId = new JPanel();
		subPaneId.setLayout(new GridLayout(1, 0));
		subPaneId.add(new JLabel("Member ID:"));
		subPaneId.add(rentalmemIdField);
		
		JPanel subPaneTitle = new JPanel();
		subPaneTitle.setLayout(new GridLayout(1, 0));
		subPaneTitle.add(new JLabel("Video Title:"));
		subPaneTitle.add(rentalVideoTitleField);
		
		JPanel subPanePlan = new JPanel();
		subPanePlan.setLayout(new GridLayout(1, 0));
		subPanePlan.add(new JLabel("Rental Plan:"));
		subPanePlan.add(planField);
		
		JPanel rentalPanel = new JPanel();
		rentalPanel.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 30 ));
		rentalPanel.setLayout(new BoxLayout(rentalPanel, BoxLayout.Y_AXIS));
		rentalPanel.add(subPaneId);
		rentalPanel.add(subPaneTitle);
		rentalPanel.add(subPanePlan);
		rentalPanel.add(Box.createRigidArea(new Dimension(10, 20)));
		rentalPanel.add(rentalRegButton);
		rentalPanel.add(Box.createRigidArea(new Dimension(10, 30)));
		rentalPanel.add(rentalScrollPane);
		rentalPanel.add(Box.createRigidArea(new Dimension(10, 20)));
		rentalPanel.add(subPanerentalDelQuit);
		
		/* ****** Video Tab ****** */
		/* Field in Video*/
		videoTitleField = new JTextField();
		videoNumInRentalField = new JTextField();
		videoNumInShopField = new JTextField();
		videoIdField = new JTextField();
		videoNameField = new JTextField();
		
		/* show button */
		videoShowButton = new JButton("Show");
		VideoShowButtonAction videoShowButtonListener = new VideoShowButtonAction();
		videoShowButton.addActionListener( videoShowButtonListener );
		
		/* clear button */
		videoClearButton = new JButton("Clear");
		VideoClearButtonAction videoClearButtonListener = new VideoClearButtonAction();
		videoClearButton.addActionListener( videoClearButtonListener );
		
		/* add or revision button for video */
		videoRegButton = new JButton("Register");
		VideoRegButtonAction videoRegButtonListener = new VideoRegButtonAction();
		videoRegButton.addActionListener( videoRegButtonListener );
		
		/* delete button for video */
		videoDelButton = new JButton("Delete");
		VideoDelButtonAction videoDelButtonListener = new VideoDelButtonAction();
		videoDelButton.addActionListener( videoDelButtonListener );
		
		/* show button for Name */
		videoNameShowButton = new JButton("Show");
		VideoNameShowButtonAction videoNameShowButtonListener = new VideoNameShowButtonAction();
		videoNameShowButton.addActionListener( videoNameShowButtonListener );
		
		/* clear button */
		videoNameClearButton = new JButton("Clear");
		VideoNameClearButtonAction videoNameClearButtonListener = new VideoNameClearButtonAction();
		videoNameClearButton.addActionListener( videoNameClearButtonListener );
		
		/* List in Video */
		videoTitleListModel = new DefaultListModel<String>();
		videoTitleList = new JList<String>(videoTitleListModel);
		videoTitleList.setVisibleRowCount(10);
		videoTitleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane videoTitleScrollPane = new JScrollPane(videoTitleList);
		videoTitleScrollPane.createVerticalScrollBar();
		videoTitleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		videoNameListModel = new DefaultListModel<String>();
		videoNameList = new JList<String>(videoNameListModel);
		videoNameList.setVisibleRowCount(10);
		videoNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane videoNameScrollPane = new JScrollPane(videoNameList);
		videoNameScrollPane.createVerticalScrollBar();
		videoNameScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		/* Panels in Video*/
		JPanel subPaneVideoTitle = new JPanel();
		subPaneVideoTitle.setLayout(new GridLayout(1, 0));
		subPaneVideoTitle.add(new JLabel("Video Title:"));
		subPaneVideoTitle.add(videoTitleField);
		
		JPanel subPaneVideoInRental = new JPanel();
		subPaneVideoInRental.setLayout(new GridLayout(1, 0));
		subPaneVideoInRental.add(new JLabel("In Rental:"));
		subPaneVideoInRental.add(videoNumInRentalField);
		
		JPanel subPaneVideoInShop = new JPanel();
		subPaneVideoInShop.setLayout(new GridLayout(1, 0));
		subPaneVideoInShop.add(new JLabel("Inventory:"));
		subPaneVideoInShop.add(videoNumInShopField);
		
		JPanel subPaneVideoShowClear = new JPanel();
		subPaneVideoShowClear.setLayout(new GridLayout(1, 0));
		subPaneVideoShowClear.add(videoShowButton);
		subPaneVideoShowClear.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneVideoShowClear.add(videoClearButton);
		
		JPanel subPaneVideoRegDel = new JPanel();
		subPaneVideoRegDel.setLayout(new GridLayout(1, 0));
		subPaneVideoRegDel.add(videoRegButton);
		subPaneVideoRegDel.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneVideoRegDel.add(videoDelButton);
		
		JPanel subPaneVideoList = new JPanel();
		subPaneVideoList.setLayout(new GridLayout(1, 0));
		JLabel videoListLabel = new JLabel("Title List:");
		videoListLabel.setHorizontalAlignment(JLabel.LEFT);
		subPaneVideoList.add(videoListLabel);
		
		JPanel subPaneVideoNameLabel = new JPanel();
		subPaneVideoNameLabel.setLayout(new GridLayout(1, 0));
		JLabel videoNameListLabel = new JLabel("At:");
		videoNameListLabel.setHorizontalAlignment(JLabel.LEFT);
		subPaneVideoNameLabel.add(videoNameListLabel);
		
		JPanel subPaneVideoNameShowClear = new JPanel();
		subPaneVideoNameShowClear.setLayout(new GridLayout(1, 0));
		subPaneVideoNameShowClear.add(videoNameShowButton);
		subPaneVideoNameShowClear.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneVideoNameShowClear.add(videoNameClearButton);
		
		JPanel subPaneVideoId = new JPanel();
		subPaneVideoId.setLayout(new GridLayout(1, 0));
		subPaneVideoId.add(new JLabel("ID:"));
		subPaneVideoId.add(videoIdField);
		
		JPanel subPaneVideoName = new JPanel();
		subPaneVideoName.setLayout(new GridLayout(1, 0));
		subPaneVideoName.add(new JLabel("Name:"));
		subPaneVideoName.add(videoNameField);
		
		JPanel midPanelVideoTitle = new JPanel();
		midPanelVideoTitle.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 15 ));
		midPanelVideoTitle.setLayout(new BoxLayout(midPanelVideoTitle, BoxLayout.Y_AXIS));
		midPanelVideoTitle.add(subPaneVideoList);
		midPanelVideoTitle.add(videoTitleScrollPane);
		midPanelVideoTitle.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelVideoTitle.add(subPaneVideoShowClear);
		midPanelVideoTitle.add(Box.createRigidArea(new Dimension(10, 20)));
		midPanelVideoTitle.add(subPaneVideoTitle);
		midPanelVideoTitle.add(subPaneVideoInRental);
		midPanelVideoTitle.add(subPaneVideoInShop);
		midPanelVideoTitle.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelVideoTitle.add(subPaneVideoRegDel);
		
		JPanel midPanelVideoName = new JPanel();
		midPanelVideoName.setBorder(BorderFactory.createEmptyBorder( 30, 30, 90, 15 ));
		midPanelVideoName.setLayout(new BoxLayout(midPanelVideoName, BoxLayout.Y_AXIS));
		midPanelVideoName.add(subPaneVideoNameLabel);
		midPanelVideoName.add(videoNameScrollPane);
		midPanelVideoName.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelVideoName.add(subPaneVideoNameShowClear);
		midPanelVideoName.add(Box.createRigidArea(new Dimension(10, 20)));
		midPanelVideoName.add(subPaneVideoId);
		midPanelVideoName.add(subPaneVideoName);
		midPanelVideoName.add(Box.createRigidArea(new Dimension(10, 10)));

		JPanel videoPanel = new JPanel();
		videoPanel.setLayout(new GridLayout(1, 0));
		videoPanel.add(midPanelVideoTitle);
		videoPanel.add(midPanelVideoName);
		
		/* ****** Member Tab ****** */
		/* Field in Member*/
		memberIdField = new JTextField();
		memberNameField = new JTextField();
		memberTitleField = new JTextField();
		memberYearField = new JTextField();
		memberMonthField = new JTextField();
		memberDayField = new JTextField();
		
		/* show button */
		memberNameShowButton = new JButton("Show");
		MemberNameShowButtonAction memberNameShowButtonListener = new MemberNameShowButtonAction();
		memberNameShowButton.addActionListener( memberNameShowButtonListener );
		
		/* clear button */
		memberNameClearButton = new JButton("Clear");
		MemberNameClearButtonAction memberNameClearButtonListener = new MemberNameClearButtonAction();
		memberNameClearButton.addActionListener( memberNameClearButtonListener );
		
		/* add or revision button for member */
		memberRegButton = new JButton("Register");
		MemberRegButtonAction memberRegButtonListener = new MemberRegButtonAction();
		memberRegButton.addActionListener( memberRegButtonListener );
		
		/* delete button for member */
		memberDelButton = new JButton("Delete");
		MemberDelButtonAction memberDelButtonListener = new MemberDelButtonAction();
		memberDelButton.addActionListener( memberDelButtonListener );
		
		/* add or revision button for Title */
		memberTitleRegButton = new JButton("Register");
		MemberTitleRegButtonAction memberTitleRegButtonListener = new MemberTitleRegButtonAction();
		memberTitleRegButton.addActionListener( memberTitleRegButtonListener );
		
		/* delete button for Title */
		memberTitleDelButton = new JButton("Delete");
		MemberTitleDelButtonAction memberTitleDelButtonListener = new MemberTitleDelButtonAction();
		memberTitleDelButton.addActionListener( memberTitleDelButtonListener );
		
		/* show button */
		memberTitleShowButton = new JButton("Show");
		MemberTitleShowButtonAction memberTitleShowButtonListener = new MemberTitleShowButtonAction();
		memberTitleShowButton.addActionListener( memberTitleShowButtonListener );
		
		/* clear button */
		memberTitleClearButton = new JButton("Clear");
		MemberTitleClearButtonAction memberTitleClearButtonListener = new MemberTitleClearButtonAction();
		memberTitleClearButton.addActionListener( memberTitleClearButtonListener );
		
		/* List in Member tab */
		memberNameListModel = new DefaultListModel<String>();
		memberNameList = new JList<String>(memberNameListModel);
		memberNameList.setVisibleRowCount(10);
		memberNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane memberNameScrollPane = new JScrollPane(memberNameList);
		memberNameScrollPane.createVerticalScrollBar();
		memberNameScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		memberTitleListModel = new DefaultListModel<String>();
		memberTitleList = new JList<String>(memberTitleListModel);
		memberTitleList.setVisibleRowCount(10);
		memberTitleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane memberTitleScrollPane = new JScrollPane(memberTitleList);
		memberTitleScrollPane.createVerticalScrollBar();
		memberTitleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		/* Panels in Member tab */
		JPanel subPaneMemberShowClear = new JPanel();
		subPaneMemberShowClear.setLayout(new GridLayout(1, 0));
		subPaneMemberShowClear.add(memberNameShowButton);
		subPaneMemberShowClear.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneMemberShowClear.add(memberNameClearButton);
		
		JPanel subPaneMemberId = new JPanel();
		subPaneMemberId.setLayout(new GridLayout(1, 0));
		subPaneMemberId.add(new JLabel("Member ID:"));
		subPaneMemberId.add(memberIdField);
		
		JPanel subPaneMemberName = new JPanel();
		subPaneMemberName.setLayout(new GridLayout(1, 0));
		subPaneMemberName.add(new JLabel("Name:"));
		subPaneMemberName.add(memberNameField);
		
		JPanel subPaneMemberRegDel = new JPanel();
		subPaneMemberRegDel.setLayout(new GridLayout(1, 0));
		subPaneMemberRegDel.add(memberRegButton);
		subPaneMemberRegDel.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneMemberRegDel.add(memberDelButton);
		
		JPanel subPaneMemberList = new JPanel();
		subPaneMemberList.setLayout(new GridLayout(1, 0));
		JLabel memberListLabel = new JLabel("Member List:");
		memberListLabel.setHorizontalAlignment(JLabel.LEFT);
		subPaneMemberList.add(memberListLabel);
		
		JPanel subPaneMemberLendList = new JPanel();
		subPaneMemberLendList.setLayout(new GridLayout(1, 0));
		JLabel memberLendListLabel = new JLabel("Title in Rental:");
		memberLendListLabel.setHorizontalAlignment(JLabel.LEFT);
		subPaneMemberLendList.add(memberLendListLabel);
		
		JPanel subPaneMemberTitleShowClear = new JPanel();
		subPaneMemberTitleShowClear.setLayout(new GridLayout(1, 0));
		subPaneMemberTitleShowClear.add(memberTitleShowButton);
		subPaneMemberTitleShowClear.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneMemberTitleShowClear.add(memberTitleClearButton);
		
		JPanel subPaneMemberTitle = new JPanel();
		subPaneMemberTitle.setLayout(new GridLayout(1, 0));
		subPaneMemberTitle.add(new JLabel("Title:"));
		subPaneMemberTitle.add(memberTitleField);
		
		JPanel subPaneMemberLimit = new JPanel();
		subPaneMemberLimit.setLayout(new GridLayout(1, 0));
		subPaneMemberLimit.add(new JLabel("Year:"));
		subPaneMemberLimit.add(memberYearField);
		subPaneMemberLimit.add(new JLabel("Month:"));
		subPaneMemberLimit.add(memberMonthField);
		subPaneMemberLimit.add(new JLabel("Date:"));
		subPaneMemberLimit.add(memberDayField);
		
		JPanel subPaneMemberTitleRegDel = new JPanel();
		subPaneMemberTitleRegDel.setLayout(new GridLayout(1, 0));
		subPaneMemberTitleRegDel.add(memberTitleRegButton);
		subPaneMemberTitleRegDel.add(Box.createRigidArea(new Dimension(30, 10)));
		subPaneMemberTitleRegDel.add(memberTitleDelButton);
		
		JPanel midPanelMemberName = new JPanel();
		midPanelMemberName.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 15 ));
		midPanelMemberName.setLayout(new BoxLayout(midPanelMemberName, BoxLayout.Y_AXIS));
		midPanelMemberName.add(subPaneMemberList);
		midPanelMemberName.add(memberNameScrollPane);
		midPanelMemberName.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelMemberName.add(subPaneMemberShowClear);
		midPanelMemberName.add(Box.createRigidArea(new Dimension(10, 20)));
		midPanelMemberName.add(subPaneMemberId);
		midPanelMemberName.add(subPaneMemberName);
		midPanelMemberName.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelMemberName.add(subPaneMemberRegDel);
		
		JPanel midPanelMemberTitle = new JPanel();
		midPanelMemberTitle.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 15 ));
		midPanelMemberTitle.setLayout(new BoxLayout(midPanelMemberTitle, BoxLayout.Y_AXIS));
		midPanelMemberTitle.add(subPaneMemberLendList);
		midPanelMemberTitle.add(memberTitleScrollPane);
		midPanelMemberTitle.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelMemberTitle.add(subPaneMemberTitleShowClear);
		midPanelMemberTitle.add(Box.createRigidArea(new Dimension(10, 20)));
		midPanelMemberTitle.add(subPaneMemberTitle);
		midPanelMemberTitle.add(subPaneMemberLimit);
		midPanelMemberTitle.add(Box.createRigidArea(new Dimension(10, 10)));
		midPanelMemberTitle.add(subPaneMemberTitleRegDel);

		JPanel memberPanel = new JPanel();
		memberPanel.setLayout(new GridLayout(1, 0));
		memberPanel.add(midPanelMemberName);
		memberPanel.add(midPanelMemberTitle);
		
		/* ******TabbedPane as Main Panel****** */
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Rental", rentalPanel);
		tabbedPane.addTab("Video", videoPanel);
		tabbedPane.addTab("Member", memberPanel);
		tabbedPane.addChangeListener(new TabChangeAction());
		return tabbedPane;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Rental Video System");
		RentalVideoShop appl = new RentalVideoShop();
		Component contents = appl.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

