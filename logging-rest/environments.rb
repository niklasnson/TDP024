configure :development do
  set :database, {adapter: "sqlite3", database: "logging.sqlite3"}
  set :show_exceptions, true
end

configure :production do
  set :database, {adapter: "sqlite3", database: "logging.sqlite3"}
  set :show_exceptions, true
end
