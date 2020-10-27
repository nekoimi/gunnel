package commands

import "github.com/spf13/cobra"

var tcpSrvCmd = &cobra.Command{
	Use:   "tcp",
	Short: "",
	RunE: func(cmd *cobra.Command, args []string) error {
		return nil
	},
}

func init() {
	rootCmd.AddCommand(tcpSrvCmd)
}
