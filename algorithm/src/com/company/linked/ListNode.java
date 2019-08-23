package com.company.linked;

import com.company.array.Array;

import java.util.List;

public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
    ListNode(int[] array){
      if(array==null|| array.length==0){
          throw new IllegalArgumentException("arr can not be empty");
      }
      this.val=array[0];
      ListNode cur=this;

      for (int i = 1; i <array.length; i++) {
          cur.next=new ListNode(array[i]);
          cur=cur.next;
      }
    }
    @Override
    public String toString(){
        StringBuilder sre=new StringBuilder();
        ListNode cre=this;
        while (cre!=null){
            sre.append(cre.val+"->");
            cre=cre.next;
        }
        sre.append("null");
        return sre.toString();
    }
}
