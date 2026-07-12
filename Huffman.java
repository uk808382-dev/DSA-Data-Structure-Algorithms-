import java.util.*;
class Node {
    char ch;
    int freq;
    Node left;
    Node right;
    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq; }
    Node(int freq, Node left, Node right) {
        this.ch = '\0';
        this.freq = freq;
        this.left = left;
        this.right = right; } }
public class Huffman {
    static PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
        public int compare(Node a, Node b) {
            return a.freq - b.freq; }
    });
    static HashMap<Character, String> codes = new HashMap<>();
    static Node root;
    static void generateCodes(Node node, String code) {
        if (node == null)
            return;
        if (node.left == null && node.right == null) {
            codes.put(node.ch, code.length() == 0 ? "0" : code);
            return;
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }
    static void buildTree(char[] chars, int[] freq) {
        pq.clear();
        codes.clear();
        for (int i = 0; i < chars.length; i++) {
            pq.add(new Node(chars[i], freq[i]));
        }
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node(left.freq + right.freq, left, right));
        }
        root = pq.poll();
        generateCodes(root, "");
    }
    static String encode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (codes.containsKey(c))
                sb.append(codes.get(c));
        }
        return sb.toString();
    }
    static String decode(String binary) {
        StringBuilder sb = new StringBuilder();
        Node current = root;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0')
                current = current.left;
            else
                current = current.right;
            if (current.left == null && current.right == null) {
                sb.append(current.ch);
                current = root;
            } }
        return sb.toString();
    }
    static void displayCodes() {
        if (codes.isEmpty()) {
            System.out.println("Generate Huffman Tree First");
            return;
        }
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Generate Huffman Tree and Codes");
            System.out.println("2. Encode a Message");
            System.out.println("3. Decode a Binary String");
            System.out.println("4. Display Character Codes");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter number of characters: ");
                    int n = sc.nextInt();
                    char[] chars = new char[n];
                    int[] freq = new int[n];
                    for (int i = 0; i < n; i++) {
                        System.out.print("Character: ");
                        chars[i] = sc.next().charAt(0);
                        System.out.print("Frequency: ");
                        freq[i] = sc.nextInt();
                    }
                    buildTree(chars, freq);
                    System.out.println("Huffman Tree Generated");
                    break;
                case 2:
                    if (codes.isEmpty()) {
                        System.out.println("Generate Huffman Tree First");
                        break;
                    }
                    sc.nextLine();
                    System.out.print("Enter message: ");
                    String message = sc.nextLine();
                    System.out.println("Encoded: " + encode(message));
                    break;
                case 3:
                    if (root == null) {
                        System.out.println("Generate Huffman Tree First");
                        break; }
                    System.out.print("Enter binary string: ");
                    String binary = sc.next();
                    System.out.println("Decoded: " + decode(binary));
                    break;
                case 4:
                    displayCodes();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice");
            } } } }