import java.util.*;

public class DotComBust {

    // 必要な変数を宣言
    private static GameHelper helper = new GameHelper();
    private static ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;

    private static void setUpGame () {
        // DotComオブジェクトを3つ作成し、各々に名前をつける
        DotCom one = new DotCom();
        one.setName("petsドットコム");
        DotCom two = new DotCom();
        two.setName("eToysドットコム");
        DotCom three = new DotCom();
        three.setName("Go2ドットコム");
        // dotComsListに作成したオブジェクトを追加する
        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);

        System.out.println("ゲームクリアの条件は3つのドットコムを沈めること");
        System.out.println("Pets.comとeToys.comとGo2.comがある");
        System.out.println("できるだけ少ない回数で全てのドットコムを沈めよう");

        for (DotCom dotComToSet : dotComsList) {
            ArrayList<String> newLocation = helper.placeDotCome(3);
            dotComToSet.setLocationCells(newLocation);
            System.out.println(newLocation);
        }
    }

    private void startPlaying() {
        while(!dotComsList.isEmpty()) {

            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
        DBOperation operation = new DBOperation();
        int[] ranking = operation.connectionDB(numOfGuesses);
        // DBに回数を登録
        // DBからランキングを取得
        finishGame(ranking);
    }

    private void checkUserGuess(String userGuess) {
        numOfGuesses++;
        String result = "miss";
        for (DotCom dotComToTest : dotComsList) {
            result = dotComToTest.checkYourself(userGuess);
            if (result.equals("hit")) {
                break;
            }
            if (result.equals("kill")) {
                dotComsList.remove(dotComToTest);
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame(int[] scoreRank) {
        System.out.println("全てのドットコムを沈めることができた！");
        if (numOfGuesses <= 18 && numOfGuesses == scoreRank[0]) {
            System.out.println("すごい！ハイスコア！");
        } else if (numOfGuesses <= 18){
            System.out.println("たった" + numOfGuesses + "回で！すごい！");
        } else {
            System.out.println(numOfGuesses + "回もかかってしまったね...");
        }
        System.out.println("スコアランキングはこちら\n1位：" + scoreRank[0] + "回" + "\n2位：" + scoreRank[1]  + "回" +"\n3位：" + scoreRank[2] + "回" );
    }

    public static void main(String[] args) {
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }
}
