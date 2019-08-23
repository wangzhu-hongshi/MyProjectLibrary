package com.company.linked;

import java.util.List;

/**]
 * 移除节点元素(形同的元素也要移除)
 */
public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        while (head!=null&&head.val==val){
            ListNode delNode=head;
            head=delNode.next;
            delNode.next=null;
        }

        if(head==null){
            return null;
        }
        ListNode prev=head;
        while (prev.next!=null){
            if(prev.next.val==val){
                ListNode delNode= prev.next;
                prev.next=delNode.next;
                delNode.next=null;
            }else
                prev=prev.next;
        }
        return head;
    }

    public static void main(String[] args) {
        LinkedList<Integer>   linkedList=new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.addFirst(i);
        }
        linkedList.addFirst(8);
        System.out.println(linkedList);


    }
}
