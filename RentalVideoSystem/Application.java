import java.util.*;
import javax.swing.*;

public class Application {
    public void rental(int id, String title, Calendar date, int plan, DefaultListModel<Member> memberListModel, DefaultListModel<Video> videoListModel, DefaultListModel<String> lendTitleListModel) throws VideoException, MemberException{
    	int i;
    	for(i=0;i<videoListModel.size(); i++) {
        	if(((videoListModel.get(i)).getTitle()).equals(title)) {
        		(videoListModel.get(i)).videoRecord(id, date, plan);
        		break;
        	}
        }
    	if(i==videoListModel.size()) {
    		throw new VideoException("No Such a Video!");
    	}
        
    	for(i=0;i<memberListModel.size(); i++){
        	if(((memberListModel.get(i)).getId()) == id) {
        		Video video = new Video(title, date, plan);
        		memberListModel.get(i).memberRecord(video);
        		lendTitleListModel.clear();
        		for(int j = 0 ; j < ((memberListModel.get(i)).getLendVideo()).size();j++) {
        			lendTitleListModel.addElement(memberListModel.get(i).getLendVideo().get(j).getTitle());
        		}
        		break;
        	}
        }
    	if(i==memberListModel.size()) {
    		throw new MemberException("No Such a Member!");
    	}
      	
    }
    
    public void removeLendVideo(int id, int memIndex, int index, DefaultListModel<Member> memberListModel, DefaultListModel<Video>  videoListModel, DefaultListModel<String>  lendTitleListModel) throws Exception{
       	int i, memId;
    	String title = "";
    	if(id != memberListModel.get(memIndex).getId()) {
    		throw new Exception();
    	}
    	memId = memberListModel.get(memIndex).getId();
    	for(i=0;i<memberListModel.size(); i++){
        	if(((memberListModel.get(i)).getId()) == memId) {
        		title = (((memberListModel.get(i)).getLendVideo()).get(index)).getTitle();
        		(memberListModel.get(i)).removeRecord(index);
        		break;
        	}
        }
    	
    	for(i=0;i<videoListModel.size(); i++) {
        	if(((videoListModel.get(i)).getTitle()).equals(title)) {
        		(videoListModel.get(i)).removeVideoRecord(memId);
        		break;
        	}
        }
    	lendTitleListModel.remove(index);
    }
    
    public void removeLendVideoRental(int memId, int index, DefaultListModel<Member> memberListModel, DefaultListModel<Video>  videoListModel, DefaultListModel<String>  lendTitleListModel) {
    	int i;
    	String title = "";
    	for(i=0;i<memberListModel.size(); i++){
        	if(((memberListModel.get(i)).getId()) == memId) {
        		title = (((memberListModel.get(i)).getLendVideo()).get(index)).getTitle();
        		(memberListModel.get(i)).removeRecord(index);
        		break;
        	}
        }

    	for(i=0;i<videoListModel.size(); i++) {
        	if(((videoListModel.get(i)).getTitle()).equals(title)) {
        		(videoListModel.get(i)).removeVideoRecord(memId);
        		break;
        	}
        }
    	
    	lendTitleListModel.remove(index);
    }
    
    public void addMember(int id, String name, DefaultListModel<Member> memberListModel, DefaultListModel<String> memberNameListModel) throws MemberIdException{
    	for(int i=0;i<memberListModel.size();i++) {
    		if(memberListModel.get(i).getId() == id) {
    			throw new MemberIdException("This ID Already Exists!");
    		}
    	}
    	Member mem = new Member(name, id);
    	memberListModel.addElement(mem);
    	memberNameListModel.addElement(name);
	}
    
	public void revMember(int index, int newId, String name, DefaultListModel<Member> memberListModel, DefaultListModel<Video> videoListModel, DefaultListModel<String> memberNameListModel) throws MemberIdException{
		int oldId;
		for(int i=0;i<memberListModel.size();i++) {
    		if(i != index && memberListModel.get(i).getId() == newId) {
    			throw new MemberIdException("This ID Already Exists!");
    		}
    	}
		oldId = memberListModel.get(index).getId();
		memberListModel.get(index).revMember(name, newId);
		memberNameListModel.remove(index);
		memberNameListModel.add(index, name);
		for(int i=0;i<videoListModel.size();i++) {
			for(int j=0;j<videoListModel.get(i).getMemberId().size();j++) {
				if(videoListModel.get(i).getMemberId().get(j) == oldId) {
					videoListModel.get(i).getMemberId().remove(j);
					videoListModel.get(i).getMemberId().add(j, newId);
				}
			}
		}
	}
	
	public void removeMember(int index, DefaultListModel<Member> memberListModel, DefaultListModel<String> memberNameListModel) throws DelException{
		if(memberListModel.get(index).getLendVideo().size() != 0) {
			throw new DelException("This Member still Has Some Videos!");
		}
		memberListModel.remove(index);
		memberNameListModel.remove(index);
		memberNameListModel.clear();
		for(int i=0; i < memberListModel.size();i++) {
			memberNameListModel.addElement(memberListModel.get(i).getName());
		}
	}
	
	public void removeVideo(int index, DefaultListModel<Video> videoListModel, DefaultListModel<String> videoTitleListModel) throws DelException{
		if(videoListModel.get(index).getMemberId().size() != 0) {
			throw new DelException("Someone Rents This Video!");
		}
		videoListModel.remove(index);
		videoTitleListModel.clear();
		for(int j=0; j < videoListModel.size();j++) {
			videoTitleListModel.addElement(videoListModel.get(j).getTitle());
		}
	}
	
    public void showLendVideo(int id, DefaultListModel<Member> memberListModel, DefaultListModel<String> memberTitleListModel) {
		memberTitleListModel.clear();
    	for(int i=0;i<memberListModel.size();i++) {
		    if(memberListModel.get(i).getId()==id) {
		    	for(int j=0;j<memberListModel.get(i).getLendVideo().size();j++) {
		    		memberTitleListModel.addElement(memberListModel.get(i).getLendVideo().get(j).getTitle());
		    	}
		    }
		}
	}
    
    public void showLendMember(String title, DefaultListModel<Video> videoListModel, DefaultListModel<String> videoNameListModel, DefaultListModel<Member> memberListModel) {
    	videoNameListModel.clear();
    	for(int i=0;i<videoListModel.size();i++) {
		    if(videoListModel.get(i).getTitle().equals(title)) {
		    	for(int j=0;j<videoListModel.get(i).getMemberId().size();j++) {
		    		int id = videoListModel.get(i).getMemberId().get(j);
		    		for(int k=0;k<memberListModel.size();k++) {
		    		    if(id == memberListModel.get(k).getId()) {
		    		    	videoNameListModel.addElement(memberListModel.get(k).getName());
		    		    }
		    		}
		    	}
		    }
		}
	}
    
    public int getRentalLimitYear(int index, String title, DefaultListModel<Member> memberListModel) {
    	int year = 0;
    	for(int i=0;i<memberListModel.get(index).getLendVideo().size();i++) {
    		if(memberListModel.get(index).getLendVideo().get(i).getTitle().equals(title)) {
    			Calendar cl = Calendar.getInstance();
    	        cl.setTime(memberListModel.get(index).getLendVideo().get(i).getLimit());
    			year = cl.get(Calendar.YEAR);
    		}
    	}
		return year;
	}
    
	public int getRentalLimitMonth(int index, String title, DefaultListModel<Member> memberListModel) {
		int month = 0;
    	for(int i=0;i<memberListModel.get(index).getLendVideo().size();i++) {
    		if(memberListModel.get(index).getLendVideo().get(i).getTitle().equals(title)) {
    			Calendar cl = Calendar.getInstance();
    	        cl.setTime(memberListModel.get(index).getLendVideo().get(i).getLimit());
    			month = cl.get(Calendar.MONTH);
    		}
    	}
		return month+1;
	}
	
	public int getRentalLimitDay(int index, String title, DefaultListModel<Member> memberListModel) {
		int day = 0;
    	for(int i=0;i<memberListModel.get(index).getLendVideo().size();i++) {
    		if(memberListModel.get(index).getLendVideo().get(i).getTitle().equals(title)) {
    			Calendar cl = Calendar.getInstance();
    	        cl.setTime(memberListModel.get(index).getLendVideo().get(i).getLimit());
    			day = cl.get(Calendar.DAY_OF_MONTH);
    		}
    	}
		return day;
	}
    
    public void revTitle(int index, int id, String title, int year, int month, int day, DefaultListModel<Member> memberListModel, DefaultListModel<Video> videoListModel, DefaultListModel<String> memberTitleListModel) throws VideoException{
		String oldTitle = "";
		int i;
		for(i=0;i<videoListModel.size();i++) {
			if(videoListModel.get(i).getTitle().equals(title)) {
				break;
			}
		}
		if(i==videoListModel.size()) {
			throw new VideoException();
		}
		oldTitle = memberTitleListModel.get(index);
		memberTitleListModel.remove(index);
		memberTitleListModel.add(index, title);
		for(i=0;i<memberListModel.size();i++) {
		    for(int j=0;j<memberListModel.get(i).getLendVideo().size();j++) {
		    	if(memberListModel.get(i).getLendVideo().get(j).getTitle().equals(oldTitle)){
		    		memberListModel.get(i).getLendVideo().get(j).changeTitleLimit(title, year, month-1, day);
		    	}
		    }
		}
	}
    
	public void revVideo(int index, String title, int inRental, int inShop, DefaultListModel<Member> memberListModel, DefaultListModel<Video> videoListModel, DefaultListModel<String> videoTitleListModel) throws VideoTitleException{
		String oldTitle="";
		for(int i=0;i<videoListModel.size();i++) {
			if(i != index && videoListModel.get(i).getTitle().equals(title)) {
				throw new VideoTitleException("This Title Already Exists!");
			}
		}
		oldTitle = videoTitleListModel.get(index);
		videoTitleListModel.remove(index);
		videoTitleListModel.add(index, title);
		for(int i=0;i<videoListModel.size();i++) {
			if(videoListModel.get(i).getTitle().equals(oldTitle)) {
				videoListModel.get(i).changeTitle(title);
				videoListModel.get(i).changeBelong(inShop + inRental);
				videoListModel.get(i).changeLend(inRental);
			}
		}
		for(int i=0;i<memberListModel.size();i++) {
		    for(int j=0;j<memberListModel.get(i).getLendVideo().size();j++){
		    	if(memberListModel.get(i).getLendVideo().get(j).getTitle().equals(oldTitle)){
		    		memberListModel.get(i).getLendVideo().get(j).changeTitle(title);
		    	}
		    }
		}
	}

	public void addVideo(String title, int inShop, DefaultListModel<Video> videoListModel, DefaultListModel<String> videoTitleListModel) throws VideoTitleException{
		for(int i=0;i<videoListModel.size();i++) {
			if(videoListModel.get(i).getTitle().equals(title)) {
				throw new VideoTitleException("This Title Already Exists!");
			}
		}
		Video video = new Video(title, inShop);
		videoListModel.addElement(video);
		videoTitleListModel.addElement(title);
	}
	
	public int getMemberIdInRental(int index, String title, DefaultListModel<Video> videoListModel) {
		int id = 0;
		for(int i=0;i<videoListModel.size();i++) {
			if(videoListModel.get(i).getTitle().equals(title)) {
				id = videoListModel.get(i).getMemberId().get(index);
			}
		}
		return id;
	}
	
	public void RefreshPanels(DefaultListModel<Video> videoListModel, DefaultListModel<Member> memberListModel, DefaultListModel<String> lendTitleListModel, DefaultListModel<String> videoTitleListModel, DefaultListModel<String> videoNameListModel, DefaultListModel<String> memberNameListModel, DefaultListModel<String> memberTitleListModel) {
		lendTitleListModel.clear();
		videoNameListModel.clear();
		memberTitleListModel.clear();
		memberNameListModel.clear();
		videoTitleListModel.clear();
		for(int i=0;i<videoListModel.size();i++) {
			videoTitleListModel.addElement(videoListModel.get(i).getTitle());
		}
		for(int i=0;i<memberListModel.size();i++) {
			memberNameListModel .addElement(memberListModel.get(i).getName());	
		}
	}
}
