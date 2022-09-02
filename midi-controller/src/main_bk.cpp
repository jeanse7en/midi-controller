// #include <SoftwareSerial.h>
// // MIDI Message Types
// const uint8_t NOTE_OFF = 0x80;
// const uint8_t NOTE_ON = 0x90;
// const uint8_t KEY_PRESSURE = 0xA0;
// const uint8_t CC = 0xB0;
// const uint8_t PROGRAM_CHANGE = 0xC0;
// const uint8_t CHANNEL_PRESSURE = 0xD0;
// const uint8_t PITCH_BEND = 0xE0;

// // MIDI notes
// const uint8_t note_A1 = 21;
// const uint8_t note_C9 = 108;

// // Roland TR-08 MIDI notes
// const uint8_t note_BassDrum = 36;
// const uint8_t note_RimShot = 37;
// const uint8_t note_SnareDrum = 38;
// const uint8_t note_HandClap = 39;
// const uint8_t note_ClosedHiHat = 42;
// const uint8_t note_LowTom = 43;
// const uint8_t note_OpenHiHat = 46;
// const uint8_t note_MidTom = 47;
// const uint8_t note_Cymbal = 49;
// const uint8_t note_HighTom = 50;
// const uint8_t note_CowBell = 56;
// const uint8_t note_HighConga = 62;
// const uint8_t note_MidConga = 63;
// const uint8_t note_LowConga = 64;
// const uint8_t note_Maracas = 70;
// const uint8_t note_Claves = 75;

// // MIDI velocities
// const uint8_t velocity_off = 0;
// const uint8_t velocity_pianississimo = 16;
// const uint8_t velocity_pianissimo = 32;
// const uint8_t velocity_piupiano = 48;
// const uint8_t velocity_mezzopiano = 64;
// const uint8_t velocity_mezzoforte = 80;
// const uint8_t velocity_forte = 96;
// const uint8_t velocity_fortissimo = 112;
// const uint8_t velocity_fortississimo = 127;

// // MIDI channel
// const uint8_t channel = 1;

// // MIDI tempo
// const uint8_t tempo = 110;            // Tempo in beats per minute
// const int eight_note = 1000; // 8th note duration in milliseconds
// SoftwareSerial MIDI_OUT_Serial(5,6);
// #define BUILTIN_LED 2
// void setup() {

//   // Set serial data rate for on MIDI OUT port
//   Serial.begin(31250);
//   // Serial.begin(9600);
//   // MIDI_OUT_Serial.begin(31250);
//   pinMode(BUILTIN_LED, OUTPUT);
// }

// void sendMIDI(uint8_t messageType, uint8_t channel, uint8_t dataByte1, uint8_t dataByte2) {

//   // Adjust zero-based MIDI channel
//   channel--;

//   // Create MIDI status byte
//   uint8_t statusByte = 0b10000000 | messageType | channel;

//   // Send MIDI status and data
//   Serial.write(statusByte);
//   Serial.write(dataByte1);
//   Serial.write(dataByte2);
//   Serial.setDebugOutput(true);
//   // MIDI_OUT_Serial.write(statusByte);
//   // MIDI_OUT_Serial.write(dataByte1);
//   // MIDI_OUT_Serial.write(dataByte2);
// }

// void loop() {

//   digitalWrite(BUILTIN_LED, LOW);
//   sendMIDI(NOTE_ON, channel, note_BassDrum, 127);
//   delay(eight_note);
//   Serial.println("Hello World!");
//   digitalWrite(BUILTIN_LED, HIGH);
//   sendMIDI(NOTE_ON, channel, note_SnareDrum, 64);
//   delay(eight_note);
  
//   digitalWrite(BUILTIN_LED, LOW);
//   sendMIDI(NOTE_ON, channel, note_BassDrum, 100);
//   delay(eight_note);
  
//   digitalWrite(BUILTIN_LED, HIGH);
//   sendMIDI(NOTE_ON, channel, note_SnareDrum, 64);
//   delay(eight_note);
  
//   digitalWrite(BUILTIN_LED, LOW);
//   sendMIDI(NOTE_ON, channel, note_BassDrum, 100);
//   delay(eight_note);
  
//   digitalWrite(BUILTIN_LED, HIGH);
//   sendMIDI(NOTE_ON, channel, note_SnareDrum, 64);
//   delay(eight_note);
  
//   digitalWrite(BUILTIN_LED, LOW);
//   sendMIDI(NOTE_ON, channel, note_BassDrum, 100);
//   sendMIDI(NOTE_ON, channel, note_HandClap, 64);
//   delay(eight_note);
  
//   digitalWrite(BUILTIN_LED, HIGH);
//   sendMIDI(NOTE_ON, channel, note_SnareDrum, 64);
//   delay(eight_note);

// }