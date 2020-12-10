package com.subei.bitoperation;

//	位运算符，二进制数运算

//	&	与	相同位都为1，才为1
//	|	或	相同位有一个为1，则为1
//	^	异或	相同位不同则为1
//	~	取反	0、1全部取反
//	<<	左移	左移，舍弃高位，低位补零
//	>>	右移	右移，舍弃低位，若为正数则左边补0，负数则左边补1
public class BitOperation {

	public static void main(String[] args) {
//		2的n次方，用于表示帖子的状态，比如热门、置顶、锁定等，例如
//		使用1表示热门，2表示置顶，4表示锁定，则用或运算符|表示计算的结果，
//		表示几种状态的叠加，例如状态为10，那就是2和8组成
		System.out.println(1|2);	//3
		System.out.println(1|4);	//5
		System.out.println(2|8);	//10
		System.out.println(1|2|8);	//11
		
		System.out.println(3&2);	//用于检查复合状态中是否包含后面那个状态
		System.out.println(5&1);

		System.out.println(1 << 1);	//左移，右边补零
		System.out.println(1 << 16);	//左移，右边补零

		
	}
}
