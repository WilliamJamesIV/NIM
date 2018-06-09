//code for the Gameboard for nim
/*methods:
getters and setters for gameboard and the players turn
print the gameboard
print if they win or not
update the game board when player makes a move
allow user to make a move
make sure move is valid
*/
public class NimGame {

  //create the gameboard and the player turn
  private int[][] nim;
  private boolean theTurn;
  //game board
  NimGame(){
    nim = new int[][] {
      {1, 0, 0, 0, 0, 0, 0},
      {1, 1, 1, 0, 0, 0, 0},
      {1, 1, 1, 1, 1, 0, 0},
      {1, 1, 1, 1, 1, 1, 1},
    };

    theTurn = true;
  }

   //returns players turn
  public boolean getTurn(){
    return theTurn;
  }

  //returns the the current status of the gameboard
  public int[][] getGameBoard()
  {
    return nim;
  }

  //sets the gameboard to previous state
  public void setGameBoard(int[][] a)
  {
    nim = a;
  }

  //prints out board using double nested loop
  public void printGameBoard() {
     for (int i = 0; i < 4; i++){
       for (int k = 0; k < 7; k++){
         System.out.print(nim[i][k]);
       }
       System.out.println("\n");
     }

     //prints out the board and then the player 1s turn
     if(this.theTurn)
       System.out.println("Player 1's turn");
     else
       System.out.println("Player 2's turn");

  }

  //check if move is valid
  public boolean moveChecker(int row, int counter){
    //checks to make sure input is ok
    if(counter>7)
      return false;
    //checks to make sure that the row is not full of 0's
    for(int i=0; i<counter; ++i){
      if(nim[row][i]==0)
        return false;
    }
    return true;
  }

  //if valid then go to the row and change the 1's to 0's depending on what the second input is
  public void moveMaker(int row, int counter){
    //makes move
    for(int i=0; i<counter; ++i){
      nim[row][i] = 0;
    }

    //switch playerTurn
    theTurn = !theTurn;
    updateGameBoard(row, counter);
  }

   //puts sticks in correct order
  private void updateGameBoard(int row, int pos){
    if(pos==7)
      return;
    if(nim[row][pos]==1){
      int counter = 0;
      System.out.print(pos);
      for(int i = pos-1; pos<nim[row].length; ++i){
        if(i == 7)
          break;
        //stick to stick and moves them to the left 1
        if(nim[row][i]==1){
          nim[row][counter] = 1;
          nim[row][i] = 0;
          counter++;
        }
      }
    }
  }

  //checks for 0s
  public boolean winChecker() {
    for(int r=0; r<nim.length; ++r){
      for(int c=0; c<nim[0].length; ++c){
        //if none, return false and continue
        if(nim[r][c]==1)
          return false;
      }
    }
    //return true and end the game
    return true;
  }

}
