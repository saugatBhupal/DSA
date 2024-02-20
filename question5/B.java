package question5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
    Assume you were hired to create an application for an ISP, and there are n network devices, such as routers,
    that are linked together to provide internet access to users. You are given a 2D array that represents network
    connections between these network devices. write an algorithm to return impacted network devices, If there is
    a power outage on a certain device, these impacted device list assist you notify linked consumers that there is a
    power outage and it will take some time to rectify an issue.
 */
public class B {
    /*Solution : We send a single to each and every node except fot the node where there is a power failure. 
     * When a node recieves the signal it is added to visited. The nodes that are not reached are added to the power failure nodes.
     * Thus, we have found the nodes that will be affected.
     */
    static class Graph{
        int v;
        ArrayList<LinkedList<Integer>> nodes;

        public Graph(int v){
            this.v = v;
            this.nodes = new ArrayList<>();
            for(int i = 0; i < v ; i++){
                nodes.add(new LinkedList<>()); 
            }
        }

        public void addNode(int s, int d){
            nodes.get(s).add(d);//Adding a bidrectional relation to teh nodes in the graph.
            nodes.get(d).add(s);
        }

        public ArrayList<Integer> getAdjacentNodes(Integer node){
            ArrayList<Integer> adjNodes = new ArrayList<>();// Creating an array and retrnging it. This array saves the nodes connected to the target node, 
            for(int i : nodes.get(node)){
                adjNodes.add(i);
            }
            return adjNodes;
        }

        public void checkSignal(int outageNode){
            Queue<Integer> toVisit = new LinkedList<>();
            boolean[] visited = new boolean[v];
            toVisit.add(0);
            visited[0] = true;
            visited[outageNode] = true;// the outage node is set to true from the beginisning to make sure i wont be added to the wqueue and neither wil its adjacent nodes.
            ArrayList<Integer> powerFailureNodes = new ArrayList<>();

            while(!toVisit.isEmpty()){ // until the queu is empty.
                int x = toVisit.poll();
                visited[x] = true;
                for(int i : getAdjacentNodes(x)){
                    if(!visited[i]){
                        toVisit.add(i);
                    }
                }
            }
            for(int i = 0; i < visited.length ; i++){
                if(!visited[i]){
                    powerFailureNodes.add(i);// checking the nodes which are not visited. adn adding them to the power failure nodes.
                }
            }
            System.out.println("Impacted Device List : " + powerFailureNodes);
        }
        

    }
    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addNode(0, 1);
        graph.addNode(0, 2);
        graph.addNode(1, 3);
        graph.addNode(1, 6);
        graph.addNode(2, 4);
        graph.addNode(4, 6);
        graph.addNode(4, 5);
        graph.addNode(5, 7);
        graph.checkSignal(4);
    }
}
