package com.company.linked;

public class Solution2 {
    public ListNode removeElements(ListNode head, int val) {
        //设置一个虚拟头节点
        ListNode dummyHede= new ListNode(-1);
        dummyHede.next=head;
        ListNode prev=dummyHede;
        while (prev.next!=null){
            if (prev.next.val==val){
                prev.next=prev.next.next;
            }else
                prev=prev.next;
        }
        return dummyHede.next;
    }

    public static void main(String[] args) {
        int[] arry={1,2,3,6,4,5,6};
        ListNode node=new ListNode(arry);
        System.out.println(node);
        Solution2 solution2=new Solution2();
        ListNode node1 = solution2.removeElements(node, 6);
        System.out.println(node1);
    }
}
