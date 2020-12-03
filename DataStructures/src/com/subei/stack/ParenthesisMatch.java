package com.subei.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// 括号匹配
public class ParenthesisMatch {

	public static void main(String[] args) {
//		String str = "{[()]}";
		String str = "()[][]";
//		String str = "{[()]}";
		
		System.out.println(checkValidParenthesis(str));
		System.out.println(check(str));
		System.out.println(validLeetCode(str));
	}
	
	public static boolean checkValidParenthesis(String str) {
		int length = str.length();	
		if (length % 2 == 1) {		//奇数个符号，那肯定不匹配
			return false;
		}
		SubeiStringStack stack = new SubeiStringStack(length);
		char[] c = str.toCharArray();
		for (char d : c) {
			if (d == '{' || d == '[' || d == '(') {
				stack.push(String.valueOf(d));	//如果遇到左括号则将其入栈
			} else {							//遇到右括号，则出栈一个元素，将右括号和元素对比，如果相配则继续循环，否则返回false	
				String left = stack.pop();
				String right = String.valueOf(d);
				if (left == null) {
					return false;
				}
				if (right.equals("}")) {
					if (!left.equals("{")) {
						return false;
					}
				} else if (right.equals("]")) {
					if (!left.equals("[")) {
						return false;
					}
				} else {
					if (!left.equals("(")) {
						return false;
					}
				}
			}
		}
		if (stack.pop() != null) {
			return false;
		}
		return true;
	}
	
//	LeetCode 官方解法
	public static boolean validLeetCode(String str) {
		if (str.length() % 2 == 1) {
			return false;
		}
		Map<Character, Character> map = new HashMap<Character, Character>();
		map.put('}', '{');
		map.put(']', '[');
		map.put(')', '(');
		Deque<Character> stack = new LinkedList<Character>();
		
		char[] chars = str.toCharArray();
		for (char c : chars) {
			if (map.containsKey(c)) {		//如果不包含说明是左括号，则入栈
				if (stack.isEmpty() || stack.peek() != map.get(c)) {	//如果不相等，说明符号不匹配，如果stack为空，说明右边符号个数多与左边，其实这里不判断也可以，因为在方法开始已经判断过符号总个数是奇数还是偶数
					return false;
				}
				stack.pop();
			} else {
				stack.push(c);
			}
		}
		return stack.isEmpty();		//如果为空，则说明匹配，如果还有值，则说明左括号多于右括号，其实这里返回true也可以，因为方法开始判断过总符号个数是奇数还是偶数了
	}
	
//	LeetCode 评论区 思路清奇网友
	public static boolean check(String str) {
		if (str.length() % 2 == 1) {
			return false;
		}
		char[] cs = str.toCharArray();
		SubeiStringStack stack = new SubeiStringStack(str.length());
		for (char c : cs) {
			if (c == '{') {
				stack.push("}");
			} else if (c == '[') {
				stack.push("]");
			} else if (c == '(') {
				stack.push(")");
			} else if (stack.isEmpty() || !String.valueOf(c).equals(stack.pop())){	
				return false;
			}
		}
		return stack.isEmpty();
	}
	
	
}
