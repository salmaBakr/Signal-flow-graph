import java.util.ArrayList;

public class Cycles {

	ArrayList<Integer> Path = new ArrayList<>();

	ArrayList<ArrayList<Integer>> AllPaths = new ArrayList<ArrayList<Integer>>();
	boolean[][] matrix = { { true, true, false, false, false },
			{ true, true, true, false, false },
			{ true, true, true, true, false },
			{ true, false, true, true, true },
			{ false, false, true, true, true } };
	String pathsString;
	ArrayList<Edge> Input_Edge;
	ArrayList<ArrayList<Edge>> AllPathsWithWeights = new ArrayList<ArrayList<Edge>>();
	
	
	boolean found(ArrayList<Integer> path, int index) {
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).equals(index)) {
				return true;
			}
		}
		return false;
	}

	void searchLoops(int Originalstart, int RecStart) {

		Path.add(RecStart);
		for (int i = 0; i < matrix[0].length; i++) {
			
			if (matrix[RecStart][i]) {
				
				if (i == Originalstart) { 
					Path.add(Originalstart);
					AllPaths.add((ArrayList<Integer>) Path.clone());
					Path.remove(Path.size() - 1);
				

				} else if (found(Path, i)) {
				} else {
					if (i >= Originalstart) {
					
						searchLoops(Originalstart, i);
						Path.remove(Path.size() - 1);
					}
				}
			}
		}
	}
	void WeightConversion() {

		for (int path = 0; path < AllPaths.size(); path++) {
			ArrayList<Edge> tempPathWithWeight = new ArrayList<Edge>();
			ArrayList<Integer> tempPath = AllPaths.get(path);
			for (int i = 0; i < tempPath.size() - 1; i++) {
				int starting = tempPath.get(i);
				int ending = tempPath.get(i + 1);
				double gain = 0;
				for (int j = 0; j < Input_Edge.size(); j++) {
					Edge temporary_edge = Input_Edge.get(j);
					if (((temporary_edge.from) == starting)
							&& ((temporary_edge.to) == ending)) {

						gain += temporary_edge.gain;

					}

				}
				Edge newEdge = new Edge(starting, ending, gain);
				tempPathWithWeight.add(newEdge);
			}
			AllPathsWithWeights.add(tempPathWithWeight);
		}

	}

	void main_menu() {
		for (int itr = 0; itr < matrix.length; itr++) {
	
			Path = new ArrayList<>();
			searchLoops(itr, itr);

		}
		WeightConversion();
		pathsString=new String();
		for (int i = 0; i < AllPaths.size(); i++) {
			pathsString+=(i+1)+"-  ";
			ArrayList<Integer> tempPath = AllPaths.get(i);
			for (int j = 0; j < tempPath.size(); j++) {
				pathsString+="a" + tempPath.get(j) + "  ";
			}
			double g=1;
			ArrayList<Edge> tem = AllPathsWithWeights.get(i);
    		for (int j = 0; j < tem.size(); j++) {
				Edge tempooo = tem.get(j);
				g=g*tempooo.gain;
			}
    		pathsString+="= "+g;
			pathsString+="\n";
		}
		for (int i = 0; i < AllPaths.size(); i++) {
			ArrayList<Integer> tempPath = AllPaths.get(i);
			for (int j = 0; j < tempPath.size(); j++) {
				System.out.print(tempPath.get(j) + "  ");
			}

			System.out.println();
		}
		System.out.println("==============");

		for (int i = 0; i < AllPathsWithWeights.size(); i++) {
			ArrayList<Edge> tempPath = AllPathsWithWeights.get(i);
			System.out.println("CYcle  ");
			System.out.println("------------");
			for (int j = 0; j < tempPath.size(); j++) {
				Edge tempooo = tempPath.get(j);
				System.out.println("from  " + tempooo.from + " to "
						+ tempooo.to + " gain " + tempooo.gain);
			}

			System.out.println();
		}
		System.out.println();


	}

}
