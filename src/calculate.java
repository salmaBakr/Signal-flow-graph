import java.util.ArrayList;
import java.util.HashSet;


public class calculate {
	ArrayList<Double> pathG = new ArrayList<Double>();
	ArrayList<Double> loopG = new ArrayList<Double>();
	ArrayList<Integer> pathm = new ArrayList<Integer>();
	ArrayList<Integer> loopm = new ArrayList<Integer>();
	ArrayList<Integer>  un= new ArrayList<Integer> ();
	
	PathLoopGeneration f;
	Cycles c;
	public calculate (PathLoopGeneration f,Cycles c)
	{
		this.f=f;
		this.c=c;
		
	}
    void fcalc()
    {
    	for(int i =0 ;i<f.AllPathsWithWeights.size();i++)
    	{
    		double g=1;
    		int l=0;
    		ArrayList<Edge> tempPath = f.AllPathsWithWeights.get(i);
    		for (int j = 0; j < tempPath.size(); j++) {
				Edge tempooo = tempPath.get(j);
				g=g*tempooo.gain;
			
					l=l|(1<<tempooo.from);
			}
    		Edge tempooo = tempPath.get(tempPath.size()-1);
    		l=l|(1<<tempooo.to);
    		pathm.add(l);
    		pathG.add(g);
    		
    	}
    	System.out.println(pathG);
    	System.out.println("+++++++++p masks++++++++++++++");
    	System.out.println(pathm);
    	
    }
    void Ccalc()
    {
    	for(int i =0 ;i<c.AllPathsWithWeights.size();i++)
    	{
    		double g=1;
    		int l=0;
    		ArrayList<Edge> tempPath = c.AllPathsWithWeights.get(i);
    		for (int j = 0; j < tempPath.size(); j++) {
				Edge tempooo = tempPath.get(j);
				g=g*tempooo.gain;
			
					l=l|(1<<tempooo.from);
				
			}
    		loopm.add(l);
    		loopG.add(g);
    		
    	}
    	System.out.println(loopG);
    	System.out.println("+++++++++l masks++++++++++++++");
    	System.out.println(loopm);
    	
    }
    String delta()
    {
    	ArrayList<Integer> m = new ArrayList<Integer>();
		for (int i = 0; i < loopm.size(); i++)
			m.add(i);
		double temp=delta(m);
		String s=new String();
		s="delta = "+temp ;
        m.clear();
    	double res=0;
    	for(int i=0;i<pathm.size();i++)
    	{
    		s+="\n";
    		
    		m.clear();
    		for(int j =0; j<loopm.size();j++)
    		{
    			if((pathm.get(i) & loopm.get(j))==0)
    			{
    				m.add(j);
    			}
    		}
    		double t=delta(m);
    		s+= "delta"+(i+1)+" = "+t;
    		res+=(t*pathG.get(i));
    		
    	}
    	res=res/temp;
    	s+="\n";
    	s+="OVERALL GAIN = "+res;
    	return s;
    }
    double delta( ArrayList<Integer> m )
    {
    	double p=1;
    	HashSet<Integer> set;
    	for(int i =1 ;i<Math.pow(2, m.size());i++)
    	{
    		boolean f= false;
    		int con=0;
    		set = new HashSet<Integer>();
    		double p2=1;
    		int h=0;
    		ArrayList<Integer> mm=new ArrayList<Integer>();
    		for( int j =0 ; j< m.size();j++)
    		{
    			ArrayList<Edge> tempPath = c.AllPathsWithWeights.get(m.get(j));
    			if( (i & (1 << j)) != 0 )
    			{
    				mm.add(j);
    				con++;
    				for(int k=0; k <tempPath.size() ;k++)
    				{
    					if(set.contains(tempPath.get(k).from))
    					{
    						f=true;
    						break;
    					}
    					else
    					{
    						set.add(tempPath.get(k).from);
    					}
    				}
    				p2=p2*loopG.get(m.get(j));
    				
    			}
    		}
    		if (f == false) {
				if (con % 2 == 1) {
					p = p + -1 * p2;
				} else
					p = p + p2;
				if(con>1)
				{
					for (int pp = 0; pp < mm.size(); pp++) {
						h = (h | (1 << mm.get(pp)));
					}
					un.add(h);
				}
			}
    		
    	}
    	return p;
    }
	public String un() {
		String str = "";
		System.out.println(un.size());
		for (int j = 0; j < un.size(); j++) {
			for (int i = 0; i < 20; i++) {
				if ((un.get(j) & (1 << i)) != 0) {

					str += (("L") + (i + 1) + " ");
				}
			}
			System.out.println(str);
			str += "\n";
		}

		return str;

	}
    
    

}
