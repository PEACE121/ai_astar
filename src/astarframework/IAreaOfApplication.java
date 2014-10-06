package astarframework;

import java.util.List;


public interface IAreaOfApplication
{
	public int getHeuristic(IState state);
	
	
	public IState getStart();
	
	
	public boolean isSolution(IState state);
	
	
	public List<IState> generateAllSuccessors(IState state);
	
	
	public int cost(IState from, IState to);
}
