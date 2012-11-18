mrsynth
=======

A hack for Music Hack Day London 2012

Inappropriately repurposing cutting edge distributed computing technologies to make the BIGGEST SOUNDING SYNTH IN THE WORLD.

Demo output:
http://soundcloud.com/davw/mrsynth-demo-track

Github:
http://github.com/davw/mrsynth

Takes tab-separated input like this:

    D   -2  0   300000  1000000 200000  SINE    0.0 1.5 0.5
    D   -3  0   400000  1000000 200000  PARTIAL_SAW 0.0 1.5     0.5
    F   0   400000  200000  2000000 500000  PARTIAL_SAW 0.0 1.0 0.5
    F   -3  800000  300000  2000000 500000  PARTIAL_SAW 0.0 1.5 0.5
    A   1   0   800000  2000000 700000  SINE    0.0 0.8 0.5

Then processes through this:

1. Massifier (Map): creates hundreds of copies of each note with subtle differences in timing, tuning, volume and panning.
2. Synthesiser (Map): generates indexed waveform data from the note definitions, one "map" to each note (each map will output thousands of waveform tuples).
3. Aggregator (Reduce): Sums the output for each waveform index.
4. Sort (Reduce): sorts the output by sample index.
5. Read back from Hadoop cluster and post-process into audio file.

Built with Java, Hadoop, and the Cascading MapReduce framework (http://www.cascading.org)