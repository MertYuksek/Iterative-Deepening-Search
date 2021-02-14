package route_finding;

import graph_impl.Destination;
import graph_impl.Graph;

import java.util.*;

public class Search_Agent {

    private Graph<Node> tGraph;
    private HashMap<State,Double> heuristic;

    public Search_Agent(Graph tGraph)
    {
        this.tGraph = tGraph;
    }

    public Search_Agent(Graph<Node> tGraph, HashMap<State, Double> heuristic) {
        this.tGraph = tGraph;
        this.heuristic = heuristic;
    }

    public void Iterative_Deepening_Search(State initialState, State goalState, int limit)
    {
        Stack<IDSNode> frontier = new Stack<>();
        Node parentNode = findNode(initialState);
        Node initialNode = parentNode;

        if(parentNode.getState() == goalState)
        {
            printPath(parentNode,parentNode);
            return;
        }
        else if(parentNode == null){
            System.out.println("Initial state is null!");
            return;
        }

        parentNode.setParentNode(parentNode);
        parentNode.setKey(parentNode);
        IDSNode tempNode = new IDSNode(parentNode);
        tempNode.setDepth(0);
        tempNode.getNode().setVisited(0);
        frontier.add(tempNode);

        int limitSize = 0;
        List<Destination<Node>> list;
        IDSNode idsParent;
        IDSNode idsNode;

        while (limitSize <= limit)
        {
            while (!frontier.isEmpty())
            {
                idsParent = frontier.pop();
                list = tGraph.getAdjacencies(idsParent.getNode().getKey());

                if(list != null && idsParent.getDepth()+1 <= limitSize)
                {
                    for (Destination<Node> node : list)
                    {
                        idsNode = new IDSNode(node.getT());
                        idsNode.getNode().setKey(node.getT());
                        idsNode.setDepth(idsParent.getDepth()+1);
                        if(node.getT().getState() == goalState)
                        {
                            idsNode.getNode().setParentNode(idsParent.getNode());
                            printPath(node.getT(),initialNode);
                            return;
                        }
                        else
                        {
                            if(!idsNode.getNode().isVisited(limitSize))
                            {
                                frontier.add(idsNode);
                                idsNode.getNode().setParentNode(idsParent.getNode());
                                idsNode.getNode().setVisited(limitSize);
                            }
                        }
                    }
                }
            }
            limitSize++;
            tempNode.getNode().setVisited(limitSize);
            frontier.add(tempNode);
        }
        System.out.println("No Path!!!");
    }

    private void printPath(Node parentNode, Node initialNode)
    {
        StringBuilder builder = new StringBuilder();
        while (parentNode.getState() != initialNode.getState())
        {
            builder.append(parentNode.getState()+" <-- ");
            parentNode = parentNode.getParentNode();
        }
        builder.append(initialNode.getState());
        System.out.println(builder);
    }

    private Node findNode(State state)
    {
        for (Object node : tGraph.keySet())
        {
            if(((Node)node).getState() == state)
            {
                return (Node) node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Env_Romania_Roads env_romania_roads = new Env_Romania_Roads();
        Search_Agent agent = new Search_Agent(env_romania_roads.getGraph());
        System.out.println("Iterative Deepening Search");
        agent.Iterative_Deepening_Search(State.Arad,State.Bucharest,3);
    }
}
