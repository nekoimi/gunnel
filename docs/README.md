# Gunnel

Gunnel 是一个基于Netty实现的内网穿透工具。

服务端：
监听9933端口，等待来自客户端的连接
根据客户端的注册信息监听多个对外暴露服务的端口
将来自每个端口的请求通过服务端-客户端管道转发给客户端
之后具体分发由客户端分发给内网各服务

客户端：
连接服务端(IP + 端口号)
客户端与被代理服务一一建立连接
将内网需要代理的服务需要暴露的信息传递给服务器端
客户端等待来自服务器的消息，根据端口号分发消息给被代理服务


```go

type LRUCache struct {
    cap int
    cache map[int]*DLinkedNode
    head, tail *DLinkedNode
}

type DLinkedNode struct {
    Key, Val int
    Prev, Next *DLinkedNode
}


func Constructor(capacity int) LRUCache {
    l := LRUCache{
        cap: capacity,
        cache: make(map[int]*DLinkedNode, capacity),
        head: &DLinkedNode{},
        tail: &DLinkedNode{},
    }
    l.head.Next = l.tail
    l.tail.Prev = l.head
    return l
}


func (this *LRUCache) Get(key int) int {
    if _, ok := this.cache[key]; !ok {
        return -1
    }
    node := this.cache[key]
    this.moveToFront(node)
    return node.Val
}


func (this *LRUCache) Put(key int, value int)  {
    if _, ok := this.cache[key]; !ok {
        node := &DLinkedNode{Key:key, Val: value}
        this.addToFront(node)
        this.cache[key] = node
        if len(this.cache) > this.cap {
            back := this.removeBack()
            delete(this.cache, back.Key)
        }
    } else {
        node := this.cache[key]
        node.Val = value
        this.moveToFront(node)
    }
}

func (this *LRUCache) addToFront(node *DLinkedNode) {
    node.Prev = this.head
    node.Next = this.head.Next
    this.head.Next.Prev = node
    this.head.Next = node
}

func (this *LRUCache) moveToFront(node *DLinkedNode) {
    this.removeNode(node)
    this.addToFront(node)
}

func (this *LRUCache) removeNode(node *DLinkedNode) {
    prev, next := node.Prev, node.Next
    prev.Next, next.Prev = next, prev
}

func (this *LRUCache) removeBack() *DLinkedNode {
    node := this.tail.Prev
    this.removeNode(node)
    return node
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * obj := Constructor(capacity);
 * param_1 := obj.Get(key);
 * obj.Put(key,value);
 */
```