import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class HashTable_6667 {
    int[] T;
    char[] A;
    int N = 7;
    int size, space;
    int index = 0;

    HashTable_6667(int size) {
        this.size = size;
        this.space = size;

        T = new int[size];
        A = new char[size * N];

        Arrays.fill(T, -1);
        Arrays.fill(A, ' ');
    }

    boolean isEmpty() {
        return space == N;
    }

    boolean isFull() {
        return space == 0;
    }

    int hash(String K, int i) {
        int k = getAscii(K);
        int temp = k % N;
        return ((temp + i * i) - 2) % N;
    }

    int getAscii(String str) {
        int v = 0;
        for(int i=0; i<str.length(); i++)
        {
            v += str.charAt(i);
        }
        return v;
    }

    void multiply(){
        HashTable_6667 H = new HashTable_6667(size*2);
        space += size;
        System.arraycopy(this.T, 0, H.T, 0, this.T.length);
        System.arraycopy(this.A, 0, H.A, 0, this.A.length);
        this.size = H.size;
        this.T = H.T;
        this.A = H.A;
    }

    int insert(String word){
        while(!isFull() || (index + word.length()) > A.length){
            this.multiply();
        }
        int j, i = 0;
        do{
            j = hash(word,i);
            if(A[j]!=' ')
                i++;
        }
        while(i < size);
        T[j] = index;
        for(int x = 0; x < word.length(); x++)
            A[index++] = word.charAt(x);
        A[index++] = '\0';
        space--;
        return j;
    }

    int search(String word){
        int ins = -1, i1 = -1, i=0;
        do{
            ins = hash(word, i++);
            i1 = T[ins];
            if(!((i1 + word.length() + 1) >= A.length) && i1 > -1 && A[i1 + word.length()] == '\\') {
                for(int j = 0; j < word.length(); j++)
                    if(word.charAt(j) != A[i1 + j])
                        break;
            }
        }while(i<size && i1 != -1);
        return ins;
    }

    int delete(String word) {
        if(!isEmpty()){
            int ind = search(word);
            if(ind != -1){
                int i = T[ind];
                while (A[i] != '\0')
                    A[i++] = '*';
                T[ind] = -2;
                space++;
            }
            return ind;
        }
        return -1;
    }

    public static void main(String[] args){
        if(args.length > 0){
            for (String arg : args) process(arg);
        }
//        process("test.txt");
    }

    public static void process(String f){
        try(BufferedReader fr = new BufferedReader(new FileReader(f))){
            String line = "";
            String[] tokens;
            HashTable_6667 h1 = null;
            while((line = fr.readLine()) != null){
                String token2 = "";
                tokens = line.split(" ");
                if(tokens.length == 2)
                    token2 = tokens[1];
                int result = 0;
                String output = "";
                int operation = Integer.parseInt(tokens[0]);
                switch (operation) {
                    case 10 -> {
                        int i = h1.insert(token2);
                        System.out.println("Inserted at index: " + i);
                    }
                    case 11 -> {
                        result = h1.delete(token2);
                        if (result < 0)
                            output = token2 + " not found!!!";
                        else
                            output = token2 + " found at index " + result + " is deleted.";
                        System.out.println(output);
                    }
                    case 12 -> {
                        result = h1.search(token2);
                        if (result < 0)
                            output = token2 + " not found!!!";
                        else
                            output = token2 + " found at index " + result;
                        System.out.println(output);
                    }
                    case 13 -> System.out.println();
                    case 14 -> h1 = new HashTable_6667(Integer.parseInt(token2));
                    default -> System.out.println("Invalid Operation!!!");
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
