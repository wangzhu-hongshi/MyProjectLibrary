package com.company.linked;

public class SoultionC {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyHede=new ListNode(-1);

        dummyHede.next=head;
        ListNode pert=dummyHede;
        while (pert.next!=null){
            if(pert.next.val==val){
                pert.next=pert.next.next;
            }else
                pert=pert.next;
        }
        return dummyHede.next;
    }
}
