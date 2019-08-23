package com.company.linked;

public class Solution3 {
    public ListNode removeElements(ListNode head, int val) {
        if(head==null){
            return null;
        }
//        ListNode res= removeElements(head.next,val);
//        if(head.val==val){
//            return res;
//        }else {
//            head.next=res;
//            return head;
//        }
        head.next=removeElements(head.next,val);
        return head.val==val?head.next:head;

    }


    public static void main(String[] args) {
        int[] arry={1,2,3,4,5,6};
        ListNode node=new ListNode(arry);
        System.out.println(node);
        Solution3 solution3=new Solution3();

        ListNode node1 = solution3.removeElements(node, 6);
        System.out.println(node1);
    }
}
