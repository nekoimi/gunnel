package commands

import (
	"github.com/nekoimi/gunnel/config"
	"github.com/spf13/cobra"
	"os"
)

var rootCmd = &cobra.Command{
	Use: "gunnels",
	Short: "A simple go program",
	Version: config.Version,
}

func init() {

}

func Execute() {
	if err := rootCmd.Execute(); err != nil {
		os.Exit(1)
	}
}
