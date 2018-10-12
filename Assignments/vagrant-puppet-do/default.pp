node 'appserver' {
  include baseconfig
  include nodejs
}

node 'dbserver' {
  include baseconfig
  include mysql
}
