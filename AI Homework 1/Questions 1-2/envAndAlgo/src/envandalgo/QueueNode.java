package envandalgo;
class QueueNode implements Comparable<Object>{
    
    public QueueNode parent;
    public coordinate point;
    public double g,h,f;
    coordinate prev;
    public int pathTotal;
    
    public QueueNode(coordinate point, int pathTotal, coordinate prev){
        this.point = point;
        this.pathTotal = pathTotal;
        this.prev = prev;
    }
    
    public QueueNode(QueueNode parent, coordinate point, double g, double h) {
    	this.parent = parent;
    	this.point = point;
    	this.g = g;
    	this.h = h;
        
    }
    
    @Override
    public int compareTo(Object o) {
    	QueueNode that = (QueueNode) o;
    	return (int)((this.g+this.h)-(that.g+that.h));
    }

   
}
