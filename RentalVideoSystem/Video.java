import java.util.*;

public class Video {
		private String title;
		private int belong;
		private int lend;
		private Date limit;
		private ArrayList<Integer> memberIds = new ArrayList<Integer>();
		private ArrayList<Date> limits = new ArrayList<Date>();
		
				
		public Video(String title, int belong) {
			this.title = title;
			this.belong = belong;
		}
		
		public Video(String title, Calendar lendDay, int plan) {
			this.title = title;
			limit = lendDay.getTime();
		}
		
		public void videoRecord(int memId, Calendar lendDay, int plan){
			lend++;
			memberIds.add(memId);
			lendDay.add(Calendar.DAY_OF_MONTH, plan);
			limits.add(lendDay.getTime());
		}
		
		public void removeVideoRecord(int memId) {
			lend--;
			for(int i=0;i<memberIds.size();i++) {
				if(memberIds.get(i) == memId) {
					memberIds.remove(i);
					limits.remove(i);
				}
			}
		}
		
		public String getTitle() {
			return title;
		}
		
		public int getBelong() {
			return belong;	
	    }
		
		public int getLend(){
			return lend;
		}
		
		public Date getLimit(){
			return limit;
		}
		
		public ArrayList<Integer> getMemberId(){
			return memberIds;
		}
		
		public ArrayList<Date> getlimits(){
			return limits;
		}
		
        public void changeBelong(int newBelong) {
        	this.belong = newBelong;
        }
        
        public void changeTitleLimit(String title, int year, int month, int day) {
        	this.title = title;
        	Calendar cal = Calendar.getInstance();
    		cal.set(year, month, day);
    		this.limit = cal.getTime();
        }
        
        public void changeTitle(String title) {
        	this.title = title;
        }
        
        public void changeLend(int lend) {
        	this.lend = lend;
        }
        
        public int getInventory() {
        	return belong - lend;
        }
}


