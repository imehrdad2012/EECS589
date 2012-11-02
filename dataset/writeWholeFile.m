function [] = writeWholeFile( id, field, text )
    if iscell(text)
        text = text{1};
    end
    handle = fopen([num2str(id), '_', field, '.csv'], 'w');
    fprintf(handle, '%s', text);
    fclose(handle);
end

