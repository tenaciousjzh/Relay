log_file = ARGV[0]
puts "generating log file: #{log_file}"

File.open log_file, "w" do |f|
  time = Time.mktime 2011, 1, 1, 0, 0, 0
  foos, bars, widgets, whosits = 1, 10, 5, 3
  (60 * 60 * 24).times do |i|
    f.puts "#{time.strftime("%Y-%m-%d %H:%M:%S")} [main] INFO - {\"foos\":#{foos},\"bars\":#{bars},\"widgets\":#{widgets},\"whosits\":#{whosits} }"
    time, foos, bars, widgets, whosits = time + 1, foos + 1, bars + 10, widgets + 5, whosits + 3
    print "."
  end
end
