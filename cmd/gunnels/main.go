package main

import (
	"fmt"
	"io"
	"net"
	"time"
)

type Conner interface {
	Read()
	Write()
}

type Client struct {
	Conn net.Conn

	readCh chan []byte
	writeCh chan []byte

	exitCh chan error

	reConn chan bool
}

func (c *Client) Read() {
	_ = c.Conn.SetDeadline(time.Now().Add(time.Second * 10))
	for {
		buf := make([]byte, 128)
		n, err := c.Conn.Read(buf)
		if err != nil && err != io.EOF {
			fmt.Println(err)
			c.exitCh <- err
		}
		if string(buf[:n]) == "ping" {
			fmt.Println("ping...")
			continue
		}

		c.readCh <- buf[:n]
	}
}

func (c *Client) Write() {
	for {
		select {
			case buf := <- c.writeCh:
				_, err := c.Conn.Write(buf)
				if err != nil {
					fmt.Println(err)
					c.exitCh <- err
				}
		}
	}
}

type User struct {
	Conn net.Conn

	readCh chan []byte
	writeCh chan []byte

	exitCh chan error
}

func (u *User) Read() {
	_ = u.Conn.SetDeadline(time.Now().Add(time.Second * 200))
	for {
		buf := make([]byte, 128)
		n, err := u.Conn.Read(buf)
		if err != nil && err != io.EOF {
			fmt.Println(err)
			u.exitCh <- err
		}
		if string(buf[:n]) == "ping" {
			fmt.Println("ping...")
			continue
		}

		u.readCh <- buf[:n]
	}
}

func (u *User) Write() {
	for {
		select {
		case buf := <- u.writeCh:
			_, err := u.Conn.Write(buf)
			if err != nil {
				fmt.Println(err)
				u.exitCh <- err
			}
		}
	}
}

func main() {
	clientListener, err := net.Listen("tcp", "0.0.0.0:8000")
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println("listen port to 8000 waiting client conn...")

	userListener, err := net.Listen("tcp", "0.0.0.0:9000")
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println("listen port to 9000 waiting user conn...")

	for {
		clientConn, err := clientListener.Accept()
		if err != nil {
			fmt.Println(err)
		}

		fmt.Printf("有Client连接， addr: " + clientConn.RemoteAddr().String())
		client := &Client{
			Conn:    clientConn,
			readCh:  make(chan []byte),
			writeCh: make(chan []byte),
			exitCh:  make(chan error),
			reConn:  make(chan bool),
		}

		var UserChan = make(chan net.Conn)

		go func(c *Client, uch chan net.Conn) {
			go c.Read()
			go c.Write()


		}(client, UserChan)
	}
}
