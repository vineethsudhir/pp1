import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class HashTable_6667 {
    int[] T;
    char[] A;
    int N = 15;
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
        int k = getAscii(K) -2;
        int temp = (k) % size;
        return (temp + i * i) % size;
    }

    int hash(String K, int i, int f){
        int k = getAscii(K) -2;
        int temp = (k) % f;
        return (temp + i * i) % f;
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
        while(isFull() || (index + word.length()) > A.length){
            this.multiply();
        }
        int i = 0;
        int j = 0;

        while(i < size) {
            j = hash(word, i);
            if (T[j] < 0) {
                T[j] = index;
                for (int x = 0; x < word.length(); x++) {
                    A[index++] = word.charAt(x);
                }
                A[index++] = '\\';
                space--;
                return j;
            }
            i++;
            if (i == size) {
                this.multiply();
            }
        }
        return -1;
    }

    int search(String word){
        int ins = -1, i1 = -1, i=0, h = size;
        while(h > 0){
            do{
                ins = hash(word, i++, h);
                i1 = T[ins];
                if(!((i1 + word.length() + 1) >= A.length) && i1 > -1 && A[i1 + word.length()] == '\\') {
                    for(int j = 0; j < word.length(); j++) {
                        if (word.charAt(j) != A[i1 + j])
                            break;
                        return ins;
                    }
                }
            }while(i<size && i1 != -1);
            h /= 2;
        }

        return -1;
    }

    int delete(String word) {
        if(!isEmpty()){
            int ind = search(word);
            if(ind >= 0){
                int i = T[ind];
                while (A[i] != '\\')
                    A[i++] = '*';
                T[ind] = -2;
                space++;
            }
            return ind;
        }
        return -1;
    }

    void pr(){
        System.out.print("T\tA: ");

        for (char c : A) {
            System.out.print(c);
        }
        System.out.println();

        for(int i = 0; i < T.length; i++)
        {
            System.out.println(i+ ": " +(T[i] < 0 ? "" : T[i]));
            //System.out.println(i+ ": " +hashTable[i]);
        }
        System.out.println();
    }

    public static void main(String[] args){
        if(args.length > 0){
            for (String arg : args) process(arg);
        }
//        process("C:\\Users\\vinee\\Documents\\Assignments\\Java\\pp1_6667\\src\\com\\vinith\\test.txt");
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
                int result;
                String output = "";
                int operation = Integer.parseInt(tokens[0]);
                switch (operation) {
                    case 10 ->{
                        h1.insert(token2);
                    }
//                        int i = h1.insert(token2);
//                        System.out.println("Inserted at index: " + i);
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
                        System.out.println(result);
                        if (result < 0)
                            output = token2 + " not found!!!";
                        else
                            output = token2 + " found at index " + result;
                        System.out.println(output);
                    }
                    case 13 -> {
                        System.out.println();
                        h1.pr();
                    }
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
