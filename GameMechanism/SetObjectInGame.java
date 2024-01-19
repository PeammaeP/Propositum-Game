package Project3_136.GameMechanism;

public class SetObjectInGame {
    The2DGamePanel MyGamePanel;
    public int MyTile = TheGameConstants.GAME_TILE_SIZE;
    public SetObjectInGame(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
    }
    private final int first_map = 0 , second_map = 1;
    public void SetMonster() {
        int i = 0;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 36 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 37 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 21 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 21 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 22 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new OrcMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 24 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 25 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 24 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 7 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 14 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 22 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 7 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 34 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 10 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 35 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 10 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 36 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 10 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 36 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 15 * MyTile;
        i++;

        MyGamePanel.MyMonster[first_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[first_map][i].curX = 36 * MyTile;
        MyGamePanel.MyMonster[first_map][i].curY = 20 * MyTile;
        i++;

        // ========= SECOND MAP ========== //

        MyGamePanel.MyMonster[second_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 36 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 37 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 20 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 21 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 22 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 29 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 27 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 23 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 7 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 24 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 7 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new SlimeMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 25 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 7 * MyTile;

        MyGamePanel.MyMonster[second_map][i] = new OrcMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 34 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 10 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 35 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 10 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 36 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 10 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new KirbyMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 36 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 15 * MyTile;
        i++;

        MyGamePanel.MyMonster[second_map][i] = new OrcMonster(MyGamePanel);
        MyGamePanel.MyMonster[second_map][i].curX = 36 * MyTile;
        MyGamePanel.MyMonster[second_map][i].curY = 20 * MyTile;

    }
    public void SetObjectGame() {
        int i=0;

        MyGamePanel.MyGameObject[first_map][i] = new Coin(MyGamePanel);
        //set the location of Coin following on the Map
        MyGamePanel.MyGameObject[first_map][i].ObjectX = 23 * MyTile;
        MyGamePanel.MyGameObject[first_map][i].ObjectY = 7 * MyTile;
        i++;

        MyGamePanel.MyGameObject[first_map][i] = new Coin(MyGamePanel);
        //set the location of Coin
        MyGamePanel.MyGameObject[first_map][i].ObjectX = 23 * MyTile;
        MyGamePanel.MyGameObject[first_map][i].ObjectY = 40 * MyTile;
        i++;

        MyGamePanel.MyGameObject[first_map][i] = new Coin(MyGamePanel);
        //set the location of Coin
        MyGamePanel.MyGameObject[first_map][i].ObjectX = 37 * MyTile;
        MyGamePanel.MyGameObject[first_map][i].ObjectY = 7 * MyTile;
        i++;

        MyGamePanel.MyGameObject[first_map][i] = new PotionRed(MyGamePanel);
        //set the location of Coin
        MyGamePanel.MyGameObject[first_map][i].ObjectX = 37 * MyTile;
        MyGamePanel.MyGameObject[first_map][i].ObjectY = 10 * MyTile;
        i++;

        MyGamePanel.MyGameObject[first_map][i] = new Boots(MyGamePanel);
        //set the location of Coin following on the Map
        MyGamePanel.MyGameObject[first_map][i].ObjectX = 23 * MyTile;
        MyGamePanel.MyGameObject[first_map][i].ObjectY = 42 * MyTile;
        i++;

        MyGamePanel.MyGameObject[first_map][i] = new PotionRed(MyGamePanel);
        //set the location of Coin following on the Map
        MyGamePanel.MyGameObject[first_map][i].ObjectX = 20 * MyTile;
        MyGamePanel.MyGameObject[first_map][i].ObjectY = 42 * MyTile;
        i++;

        MyGamePanel.MyGameObject[second_map][i] = new WoodDoor(MyGamePanel);
        //set the location of Door following on the Map
        MyGamePanel.MyGameObject[second_map][i].ObjectX = 10 * MyTile;
        MyGamePanel.MyGameObject[second_map][i].ObjectY = 12 * MyTile;
        i++;

        MyGamePanel.MyGameObject[second_map][i] = new WoodDoor(MyGamePanel);
        //set the location of Door following on the Map
        MyGamePanel.MyGameObject[second_map][i].ObjectX = 8 * MyTile;
        MyGamePanel.MyGameObject[second_map][i].ObjectY = 28 * MyTile;
        i++;

        MyGamePanel.MyGameObject[second_map][i] = new WoodDoor(MyGamePanel);
        //set the location of Door following on the Map
        MyGamePanel.MyGameObject[second_map][i].ObjectX = 12 * MyTile;
        MyGamePanel.MyGameObject[second_map][i].ObjectY = 23 * MyTile;
        i++;

        MyGamePanel.MyGameObject[second_map][i] = new Chests(MyGamePanel);
        MyGamePanel.MyGameObject[second_map][i].ObjectX = 10 * MyTile;
        MyGamePanel.MyGameObject[second_map][i].ObjectY = 7 * MyTile;
    }
}

