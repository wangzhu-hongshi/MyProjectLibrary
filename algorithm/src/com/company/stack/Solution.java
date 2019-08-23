package com.company.stack;

import java.util.Stack;

public class Solution {
    public static boolean isValid(String s){
        Stack<Character> stack=new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c== '(' ||c== '['|| c=='{'){
                 stack.push(c);
            }else {
                if(stack.isEmpty())
                    return false;
                char pop = stack.pop();
                if(c==')'&& pop!= '(')
                    return false;
                if(c==']'&& pop!='[')
                    return false;
                if(c=='}'&& pop!='{')
                    return false;
            }

        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        boolean valid = isValid("([");
        System.out.println(valid);
    }
}
