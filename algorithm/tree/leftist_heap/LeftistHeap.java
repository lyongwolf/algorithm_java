class LeftistHeap {
    int[] val;
    int[] lc, rc, fa, dis, root;

    public LeftistHeap(int[] val) {
        this.val = val.clone();
        int n = val.length;
        lc = new int[n];
        rc = new int[n];
        fa = new int[n];
        dis = new int[n];
        root = new int[n];
        dis[0] = -1;
        for (int i = 1; i < n; i++) {
            root[i] = i;
        }
    }

    // 若对已删除节点进行 merge 操作，merge 操作结束后必须将新头 i 的 rootNo[i] 设置为 i
    // 因为此时 i 可能是由非头已删除节点得到，在非头删除机制下 rootNo[i] 会指向 i 的 左右儿子节点 merge 成的新节点
    int merge(int i, int j) {
        if (i == 0 || j == 0) {
            return i + j;
        }
        if (val[i] < val[j]) {// 比较逻辑
            i ^= j ^ (j = i);
        }
        rc[i] = merge(rc[i], j);
        fa[rc[i]] = i;
        if (dis[lc[i]] < dis[rc[i]]) {
            lc[i] ^= rc[i] ^ (rc[i] = lc[i]);
        }
        dis[i] = dis[rc[i]] + 1;
        root[lc[i]] = root[rc[i]] = i;
        return i;
    }

    // i 是树的根节点，删除 i 并返回新树头节点编号
    int poll(int i) {
        root[lc[i]] = lc[i];
        root[rc[i]] = rc[i];
        root[i] = merge(lc[i], rc[i]);
        fa[i] = lc[i] = rc[i] = dis[i] = 0;
        return root[i];
    }

    // 删除节点 i ，返回新树头节点编号
    int remove(int i) {
        int h = rootNo(i);
        root[lc[i]] = lc[i];
        root[rc[i]] = rc[i];
        int s = merge(lc[i], rc[i]);
        int f = fa[i];
        root[i] = s;
        fa[s] = f;
        if (h != i) {
            root[s] = h;
            if (lc[f] == i) {
                lc[f] = s;
            } else {
                rc[f] = s;
            }
            for (int d = dis[s]; dis[f] > d + 1; f = fa[f], d++) {
                dis[f] = d + 1;
                if (dis[lc[f]] < dis[rc[f]]) {
                    lc[f] ^= rc[f] ^ (rc[f] = lc[f]);
                }
            }
        }
        fa[i] = lc[i] = rc[i] = dis[i] = 0;
        return root[s];
    }
    
    int rootNo(int i) {
        return root[i] == i ? i : (root[i] = rootNo(root[i]));
    }

}