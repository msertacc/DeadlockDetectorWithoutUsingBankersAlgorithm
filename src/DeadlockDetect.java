import java.util.ArrayList;
import java.util.Arrays;

public class DeadlockDetect {
    public static void main(String[] args) {
        System.out.println("************DEADLOCK DETECTOR********************");
        int[] availableArray = {0, 0, 0};
        int[][] allocArray= {{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
        int[][] maxArray = {{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
        int[] instanceArray = {7, 2, 6};
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)); //process numbers
        ArrayList<Integer> orderedList = new ArrayList<>(); //process numbers
        boolean[] finishArray = {false, false, false, false, false};
        int temp = 0;
        for(int j = 0;j < allocArray[0].length;j++){
            for(int i = 0; i< allocArray.length;i++ ){
                temp += allocArray[i][j];
                if(i == allocArray.length - 1){
                    availableArray[j] = availableArray[j] + temp - instanceArray[j];
                    temp = 0;
                }
            }
        }
        int kontrol;
        int j = 0;
        int startPoint = 0;
        int deadlockControl = -1;
        System.out.println("----------------------------------------------------------------");
        System.out.println("Processes    Allocation    Request    Available");
        for(int i=0;i<allocArray.length;i++){
            System.out.print("P"+(i));
            System.out.print("           ");
            for(int k=0;k<allocArray[0].length;k++){
                System.out.print("  "+allocArray[i][k]);
            }
            System.out.print("   ");
            for(int l=0;l<allocArray[0].length;l++){
                System.out.print("  "+maxArray[i][l]);
            }
            System.out.print("   ");
            if(i==0){
                for(int m=0;m<allocArray[0].length;m++){
                    System.out.print("  "+availableArray[m]);
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------");
        while(arrayList.size() > 0){
            kontrol = 0;
            if(startPoint == j)
                deadlockControl++;
            if(arrayList.contains(j)){
                for(int i = 0; i < availableArray.length; i++){
                    if(availableArray[i] >= maxArray[j][i])
                        kontrol ++;
                }
            }
            if(kontrol == availableArray.length){
                for (int h = 0; h < availableArray.length; h++){
                    availableArray[h] += allocArray[j][h];
                }
                finishArray[j] = true;
                if(arrayList.contains(j)){
                    orderedList.add(j);
                    int index = arrayList.indexOf(j);
                    arrayList.remove(index);
                }
                if (arrayList.size() != 0)
                    startPoint = arrayList.get(0);
            }
            j++;
            if(j >= maxArray.length - 1){
                j = startPoint;
            }
            if(deadlockControl > maxArray.length){
                System.out.println("System in Deadlock!");
                System.out.print("Process: ");
                if(orderedList.size() > 0){
                    for (Integer p : orderedList){
                        System.out.print("P" + p + " ");
                    }
                    System.out.println("worked, but others locked!");
                }
                else
                    System.out.println("All process locked!");

                for(int i = 0; i < finishArray.length; i++){
                    System.out.print("P["+i+"] " +finishArray[i]+ " | ");
                }
                System.out.println("\n----------------------------------------------------------------");
                System.exit(0);
            }
        }
        System.out.print("Process: ");
        for (Integer p : orderedList){
            System.out.print("P" + p + " ");
        }
        System.out.println("\nNo Deadlock!");
        for(int i = 0; i < finishArray.length; i++){
            System.out.print("P["+i+"] " +finishArray[i]+ " | ");
        }
        System.out.println("\n----------------------------------------------------------------");
    }
}
