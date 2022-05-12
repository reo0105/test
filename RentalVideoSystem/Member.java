import java.util.*;


public class Member {
		private String name;
		private int id;
		private ArrayList<Video> lendVideo = new ArrayList<Video>();
		
		public Member(String name, int id) {
			this.name = name;
			this.id = id;
		}
		
		public void memberRecord(Video video) {
		    lendVideo.add(video);
		}
		
		public void removeRecord(int index) {
			lendVideo.remove(index);
		}
		
		public void revMember(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public String getName() {
			return name;	
	    }
		
		public int getId(){
			return id;
		}
		
		public ArrayList<Video> getLendVideo(){
			return lendVideo;
		}
}


