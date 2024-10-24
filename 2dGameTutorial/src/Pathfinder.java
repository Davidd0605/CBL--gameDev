import java.util.ArrayList;

public class Pathfinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openlist = new ArrayList<>() ;
    public ArrayList<Node> pathList = new ArrayList<>() ;
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes(){
        node = new Node[PerlinGenerator.mapSize][PerlinGenerator.mapSize];

        int col =0;
        int row = 0;

        while(col < PerlinGenerator.mapSize && row < PerlinGenerator.mapSize){

            node[col][row] = new Node(col,row);


            col++;
            if(col == PerlinGenerator.mapSize){
                col = 0;
                row++;
            }
        }
    }
    public void resetNodes(){
        int col=0;
        int row = 0;

        while(col< PerlinGenerator.mapSize && row < PerlinGenerator.mapSize){

            //Reset the state of the nodes (open,solid,checked)
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == PerlinGenerator.mapSize){
                col = 0;
                row++;
            }
        }

        //Reset other settings
        openlist.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();

        //Set start and goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openlist.add(currentNode);

        int col =0;
        int row = 0;

        while(col < PerlinGenerator.mapSize && row < PerlinGenerator.mapSize){

            //Set SOLID NODE
            int tileNum = gp.tileManager.mapTileNum[col][row];
            if(gp.tileManager.tile[tileNum].collision){
                node[col][row].solid = true;
            }

            //CHECK INTERACTABLE (LEAVE FOR LATER)
            if(gp.tileManager.tile[tileNum].interactable){
                node[col][row].solid = true;
            }

            //SET COST

            getCost(node[col][row]);

            col++;
            if(col == PerlinGenerator.mapSize){
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node){

        //G cost( the cost of traversing the tile)
        node.gCost = getDistance(node.col - startNode.col, node.row - startNode.row);
        //H cost
        node.hCost = getDistance(node.col - goalNode.col, node.row - goalNode.row);
        //F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            //Check the current node
            currentNode.checked = true;
            openlist.remove(currentNode);

            // Open the Up node
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            //Open the left node
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }
            //Open the up-left node
            if(row - 1 >= 0 && col - 1 >= 0){
                openNode(node[col-1][row-1]);
            }
            //Open the up-right node
            if(row - 1 >= 0 && col + 1 < PerlinGenerator.mapSize){
                openNode(node[col+1][row-1]);
            }
            //Open the down-left node
            if(row + 1 < PerlinGenerator.mapSize && col - 1 >= 0){
                openNode(node[col-1][row+1]);
            }
            //Open the down-right node
            if(row + 1 < PerlinGenerator.mapSize && col + 1 < PerlinGenerator.mapSize){
                openNode(node[col+1][row+1]);
            }
            //Open the down node
            if(row + 1 < PerlinGenerator.mapSize){
                openNode(node[col][row+1]);
            }
            //Open the right node
            if(col + 1 < PerlinGenerator.mapSize){
                openNode(node[col+1][row]);
            }

            //Find the best node
            int bestNodeIndex = 0;
            double bestNodefCost = 999;

            for(int i = 0 ; i < openlist.size() ; i++){
                //Check if this node's F cost is better
                if(openlist.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openlist.get(i).fCost;
                }
                //If F cost is equal, check the G cost
                else if(openlist.get(i).fCost == bestNodefCost){
                    if(openlist.get(i).gCost < openlist.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            //If there is no node in the openlist, end the loop
            if(openlist.size() == 0){
                break;
            }

            //After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openlist.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){

            node.open = true;
            node.parent = currentNode;
            openlist.add(node);

        }
    }

    public void trackThePath(){
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }

    public double getDistance(int x,int  y){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
