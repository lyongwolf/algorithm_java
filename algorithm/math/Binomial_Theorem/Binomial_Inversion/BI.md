# 二项式反演的四种形式：

## 形式一：
$$
g(n) = \sum_{i = 0}^{n}(-1)^i{n \choose i}f(i) 
\iff 
f(n) = \sum_{i = 0}^{n}(-1)^i{n \choose i}g(i)
$$

## 形式二：
$$
g(n) = \sum_{i = 0}^{n}{n \choose i}f(i) 
\iff 
f(n) = \sum_{i = 0}^{n}(-1)^{n - i}{n \choose i}g(i)
$$

## 形式三：
$$
g(n) = \sum_{i = n}^{N}(-1)^i{i \choose n}f(i) 
\iff 
f(n) = \sum_{i = n}^{N}(-1)^i{i \choose n}g(i)
$$

## 形式四：
$$
g(n) = \sum_{i = n}^{N}{i \choose n}f(i) 
\iff 
f(n) = \sum_{i = n}^{N}(-1)^{i - n}{i \choose n}g(i)
$$

