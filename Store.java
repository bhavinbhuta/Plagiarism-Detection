
import java.util.Comparator;

public class Store implements Comparable<Store>{
	
		private int index;
		private long differ;
		
		public Store(int index, long differ)
		{
			this.index=index;
			this.differ=differ;
		}
		
		public int getIndex()
		{
			return index;
		}
		
		public long getDiffer()
		{
			return differ;
		}
		 
		public void setIndex(int files) 
		{
			this.index = files; 
		}
		
	    public void setDiffer(long differ) 
	    { 
	    	this.differ = differ; 
	    }
	    public int compareTo(Store st) {
	    	Processing.textArea.append("Sorting the data to retrieve closest images.."+"\n");
	        return (int) (this.differ - st.differ);
	    }
	 
	    @Override
	    public String toString() {
	        return "[id=" + this.index + ", differences!!=" + this.differ + "]";
	    }

}
	
