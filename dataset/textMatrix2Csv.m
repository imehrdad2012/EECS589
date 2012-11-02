function [ csv ] = textMatrix2Csv( m )
%TEXTMATRIX Converts a matrix with string values (that are represented as doubles) to a CSV file
  csv = '';
  for row=1:size(m,1)
      csvRow = '';
      for col=1:size(m,2)
          element = m(row,col);
          if iscell(element)
              element = element{1};
          end
          csvRow = [csvRow, num2str(element), ','];
      end
      csv = sprintf('%s%s\n',csv, csvRow);
  end
end

