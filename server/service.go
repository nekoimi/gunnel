package server

import (
	"github.com/nekoimi/gunnel/config"
	"net"
)

type NetListener net.Listener

type Service struct {
	tcpListener NetListener
	srvCfg      config.ServerConfig
}

func NewSrv(cfg config.ServerConfig) (srv *Service, err error) {
	srv = &Service{
		srvCfg: cfg,
	}
	return
}
