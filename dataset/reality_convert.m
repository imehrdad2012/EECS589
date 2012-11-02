for index=1:size(s,2)
    entry = s(index);
    disp([num2str(index), '/', num2str(size(s,2))]);
    writeWholeFile(index, 'imei', entry.imei);
    writeWholeFile(index, 'hashedNumber', entry.my_hashedNumber);
    writeWholeFile(index, 'locs', textMatrix2Csv(entry.locs));
    writeWholeFile(index, 'all_locs', textMatrix2Csv(entry.all_locs));
    writeWholeFile(index, 'cell_names', textMatrix2Csv(entry.cellnames));
end