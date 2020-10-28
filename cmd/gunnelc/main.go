package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"os"
)

func main() {
	fmt.Println("client")

	conn, err := net.Dial("tcp", ":8000")
	if err != nil {
		log.Fatalln(err)
	}
	defer conn.Close()

	inputReader := bufio.NewReader(os.Stdin)

	for {
		str, err := inputReader.ReadString('\n')
		if err != nil {
			log.Fatalln(err)
		}

		conn.Write([]byte(str))
	}
}
