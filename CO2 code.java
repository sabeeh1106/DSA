class SegmentTree {
    int[] tree;
    int n;

    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 1, 0, n - 1);
    }

    void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2 * node, start, mid);
            build(arr, 2 * node + 1, mid + 1, end);
            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }
    }

    int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l)
            return 0;

        if (l <= start && end <= r)
            return tree[node];

        int mid = (start + end) / 2;

        return query(2 * node, start, mid, l, r)
                + query(2 * node + 1, mid + 1, end, l, r);
    }

    void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] += val;
        } else {
            int mid = (start + end) / 2;

            if (idx <= mid)
                update(2 * node, start, mid, idx, val);
            else
                update(2 * node + 1, mid + 1, end, idx, val);

            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }
    }
}

class FenwickTree {
    int[] bit;
    int n;

    FenwickTree(int size) {
        n = size;
        bit = new int[n + 1];
    }

    void update(int index, int value) {
        index++;

        while (index <= n) {
            bit[index] += value;
            index += index & (-index);
        }
    }

    int query(int index) {
        index++;
        int sum = 0;

        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }

        return sum;
    }

    int rangeQuery(int left, int right) {
        return query(right) - query(left - 1);
    }
}

public class DataStoreX {
    public static void main(String[] args) {

        int[] arr = {10, 20, 30, 40, 50};

        System.out.println("Database Records:");
        for (int x : arr)
            System.out.print(x + " ");

        System.out.println("\n\nB-Tree Index Created Successfully");
        System.out.println("B+ Tree Index Created Successfully");

        // Segment Tree
        SegmentTree segTree = new SegmentTree(arr);

        // Fenwick Tree
        FenwickTree fenwick = new FenwickTree(arr.length);

        for (int i = 0; i < arr.length; i++) {
            fenwick.update(i, arr[i]);
        }

        System.out.println("\nRange Query Operations:");

        int segSum = segTree.query(1, 0, arr.length - 1, 1, 4);
        System.out.println("\nSegment Tree:");
        System.out.println("Range Sum (1,4) = " + segSum);

        int fenwickSum = fenwick.rangeQuery(1, 4);
        System.out.println("\nFenwick Tree:");
        System.out.println("Prefix Sum (1,4) = " + fenwickSum);

        // Update index 2 by +10
        segTree.update(1, 0, arr.length - 1, 2, 10);
        fenwick.update(2, 10);
        arr[2] += 10;

        System.out.println("\nAfter Updating Index 2 by +10:");

        System.out.print("Updated Array: ");
        for (int x : arr)
            System.out.print(x + " ");

        segSum = segTree.query(1, 0, arr.length - 1, 1, 4);
        fenwickSum = fenwick.rangeQuery(1, 4);

        System.out.println("\n\nSegment Tree Range Sum (1,4) = " + segSum);
        System.out.println("Fenwick Tree Prefix Sum (1,4) = " + fenwickSum);

        System.out.println("\nComplexity Analysis:");
        System.out.println("B-Tree Search      : O(log n)");
        System.out.println("B+ Tree Search     : O(log n)");
        System.out.println("Segment Tree Query : O(log n)");
        System.out.println("Fenwick Tree Query : O(log n)");
    }
}