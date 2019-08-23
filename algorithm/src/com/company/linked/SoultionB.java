package com.company.linked;



public class SoultionB {
    public ListNode removeElements(ListNode head,int val,int depth){
        String depthString = generateDepthString(depth);
        System.out.print(depthString);
        System.out.println("Call: remove"+ val+"in"+head);
        if(head==null){
            System.out.print(depthString);
            System.out.println("Return: "+head);
            return null;
        }
        ListNode res=removeElements(head.next,val,depth+1);
        System.out.print(depthString);
        System.out.println("After remove"+val+":"+res);
        ListNode ret;
        if(head.val==val){
            ret=res;
        }else{
            head.next=res;
            ret=head;
        }
        System.out.print(depthString);
        System.out.println("Return: "+ret);
        return ret;

    }
    private String generateDepthString (int depth){
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < depth; i++)
            builder.append("--");
        return builder.toString();
    }

    public static void main(String[] args) {
        int[] nums={1,2,4,3,5,6,3};
        ListNode listNode=new ListNode(nums);
        System.out.println(listNode);
        ListNode listNode1 = new SoultionB().removeElements(listNode, 3,0);
        System.out.println(listNode);
    }
}
