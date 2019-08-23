package com.company.linked;

import javax.xml.soap.Node;

public class SolutionA {
    public ListNode removeElements(ListNode head,int val){
        while (head!=null && head.val==val){
            head=head.next;
        }
        if(head==null){
            return null;
        }
        ListNode ret=head;
        while (ret.next!=null){
            if(ret.next.val==val){
                ret.next=ret.next.next;
            }else {
                ret=ret.next;
            }
        }
        return head;

    }
}
