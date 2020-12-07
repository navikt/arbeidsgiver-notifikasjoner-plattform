/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package no.nav.arbeidsgiver.notifikasjon;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Done extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8176018361184215813L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Done\",\"namespace\":\"no.nav.arbeidsgiver.notifikasjon\",\"fields\":[{\"name\":\"tidspunkt\",\"type\":\"long\",\"logicalType\":\"timestamp-millis\"},{\"name\":\"mottaker\",\"type\":{\"type\":\"record\",\"name\":\"Mottaker\",\"fields\":[{\"name\":\"mottaker\",\"type\":[{\"type\":\"record\",\"name\":\"OrgnrFnr\",\"doc\":\"Mottas av dem med gitt fnr i konteksten av et bestemt orgnr\",\"fields\":[{\"name\":\"orgnr\",\"type\":\"string\"},{\"name\":\"fnr\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"OrgnrAltinnservice\",\"doc\":\"Mottas av alle som har tilgang til altinn-tjenesten i det gitte orgnr\",\"fields\":[{\"name\":\"orgnr\",\"type\":\"string\"},{\"name\":\"servicecode\",\"type\":\"string\"},{\"name\":\"serviceedition\",\"type\":\"string\"}]}]}]}},{\"name\":\"grupperingsId\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Done> ENCODER =
      new BinaryMessageEncoder<Done>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Done> DECODER =
      new BinaryMessageDecoder<Done>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Done> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Done> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Done> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Done>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Done to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Done from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Done instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Done fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private long tidspunkt;
   private no.nav.arbeidsgiver.notifikasjon.Mottaker mottaker;
   private java.lang.CharSequence grupperingsId;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Done() {}

  /**
   * All-args constructor.
   * @param tidspunkt The new value for tidspunkt
   * @param mottaker The new value for mottaker
   * @param grupperingsId The new value for grupperingsId
   */
  public Done(java.lang.Long tidspunkt, no.nav.arbeidsgiver.notifikasjon.Mottaker mottaker, java.lang.CharSequence grupperingsId) {
    this.tidspunkt = tidspunkt;
    this.mottaker = mottaker;
    this.grupperingsId = grupperingsId;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return tidspunkt;
    case 1: return mottaker;
    case 2: return grupperingsId;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: tidspunkt = (java.lang.Long)value$; break;
    case 1: mottaker = (no.nav.arbeidsgiver.notifikasjon.Mottaker)value$; break;
    case 2: grupperingsId = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'tidspunkt' field.
   * @return The value of the 'tidspunkt' field.
   */
  public long getTidspunkt() {
    return tidspunkt;
  }


  /**
   * Sets the value of the 'tidspunkt' field.
   * @param value the value to set.
   */
  public void setTidspunkt(long value) {
    this.tidspunkt = value;
  }

  /**
   * Gets the value of the 'mottaker' field.
   * @return The value of the 'mottaker' field.
   */
  public no.nav.arbeidsgiver.notifikasjon.Mottaker getMottaker() {
    return mottaker;
  }


  /**
   * Sets the value of the 'mottaker' field.
   * @param value the value to set.
   */
  public void setMottaker(no.nav.arbeidsgiver.notifikasjon.Mottaker value) {
    this.mottaker = value;
  }

  /**
   * Gets the value of the 'grupperingsId' field.
   * @return The value of the 'grupperingsId' field.
   */
  public java.lang.CharSequence getGrupperingsId() {
    return grupperingsId;
  }


  /**
   * Sets the value of the 'grupperingsId' field.
   * @param value the value to set.
   */
  public void setGrupperingsId(java.lang.CharSequence value) {
    this.grupperingsId = value;
  }

  /**
   * Creates a new Done RecordBuilder.
   * @return A new Done RecordBuilder
   */
  public static no.nav.arbeidsgiver.notifikasjon.Done.Builder newBuilder() {
    return new no.nav.arbeidsgiver.notifikasjon.Done.Builder();
  }

  /**
   * Creates a new Done RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Done RecordBuilder
   */
  public static no.nav.arbeidsgiver.notifikasjon.Done.Builder newBuilder(no.nav.arbeidsgiver.notifikasjon.Done.Builder other) {
    if (other == null) {
      return new no.nav.arbeidsgiver.notifikasjon.Done.Builder();
    } else {
      return new no.nav.arbeidsgiver.notifikasjon.Done.Builder(other);
    }
  }

  /**
   * Creates a new Done RecordBuilder by copying an existing Done instance.
   * @param other The existing instance to copy.
   * @return A new Done RecordBuilder
   */
  public static no.nav.arbeidsgiver.notifikasjon.Done.Builder newBuilder(no.nav.arbeidsgiver.notifikasjon.Done other) {
    if (other == null) {
      return new no.nav.arbeidsgiver.notifikasjon.Done.Builder();
    } else {
      return new no.nav.arbeidsgiver.notifikasjon.Done.Builder(other);
    }
  }

  /**
   * RecordBuilder for Done instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Done>
    implements org.apache.avro.data.RecordBuilder<Done> {

    private long tidspunkt;
    private no.nav.arbeidsgiver.notifikasjon.Mottaker mottaker;
    private no.nav.arbeidsgiver.notifikasjon.Mottaker.Builder mottakerBuilder;
    private java.lang.CharSequence grupperingsId;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(no.nav.arbeidsgiver.notifikasjon.Done.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.tidspunkt)) {
        this.tidspunkt = data().deepCopy(fields()[0].schema(), other.tidspunkt);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.mottaker)) {
        this.mottaker = data().deepCopy(fields()[1].schema(), other.mottaker);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasMottakerBuilder()) {
        this.mottakerBuilder = no.nav.arbeidsgiver.notifikasjon.Mottaker.newBuilder(other.getMottakerBuilder());
      }
      if (isValidValue(fields()[2], other.grupperingsId)) {
        this.grupperingsId = data().deepCopy(fields()[2].schema(), other.grupperingsId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing Done instance
     * @param other The existing instance to copy.
     */
    private Builder(no.nav.arbeidsgiver.notifikasjon.Done other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.tidspunkt)) {
        this.tidspunkt = data().deepCopy(fields()[0].schema(), other.tidspunkt);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.mottaker)) {
        this.mottaker = data().deepCopy(fields()[1].schema(), other.mottaker);
        fieldSetFlags()[1] = true;
      }
      this.mottakerBuilder = null;
      if (isValidValue(fields()[2], other.grupperingsId)) {
        this.grupperingsId = data().deepCopy(fields()[2].schema(), other.grupperingsId);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'tidspunkt' field.
      * @return The value.
      */
    public long getTidspunkt() {
      return tidspunkt;
    }


    /**
      * Sets the value of the 'tidspunkt' field.
      * @param value The value of 'tidspunkt'.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder setTidspunkt(long value) {
      validate(fields()[0], value);
      this.tidspunkt = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'tidspunkt' field has been set.
      * @return True if the 'tidspunkt' field has been set, false otherwise.
      */
    public boolean hasTidspunkt() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'tidspunkt' field.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder clearTidspunkt() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'mottaker' field.
      * @return The value.
      */
    public no.nav.arbeidsgiver.notifikasjon.Mottaker getMottaker() {
      return mottaker;
    }


    /**
      * Sets the value of the 'mottaker' field.
      * @param value The value of 'mottaker'.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder setMottaker(no.nav.arbeidsgiver.notifikasjon.Mottaker value) {
      validate(fields()[1], value);
      this.mottakerBuilder = null;
      this.mottaker = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'mottaker' field has been set.
      * @return True if the 'mottaker' field has been set, false otherwise.
      */
    public boolean hasMottaker() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'mottaker' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public no.nav.arbeidsgiver.notifikasjon.Mottaker.Builder getMottakerBuilder() {
      if (mottakerBuilder == null) {
        if (hasMottaker()) {
          setMottakerBuilder(no.nav.arbeidsgiver.notifikasjon.Mottaker.newBuilder(mottaker));
        } else {
          setMottakerBuilder(no.nav.arbeidsgiver.notifikasjon.Mottaker.newBuilder());
        }
      }
      return mottakerBuilder;
    }

    /**
     * Sets the Builder instance for the 'mottaker' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder setMottakerBuilder(no.nav.arbeidsgiver.notifikasjon.Mottaker.Builder value) {
      clearMottaker();
      mottakerBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'mottaker' field has an active Builder instance
     * @return True if the 'mottaker' field has an active Builder instance
     */
    public boolean hasMottakerBuilder() {
      return mottakerBuilder != null;
    }

    /**
      * Clears the value of the 'mottaker' field.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder clearMottaker() {
      mottaker = null;
      mottakerBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'grupperingsId' field.
      * @return The value.
      */
    public java.lang.CharSequence getGrupperingsId() {
      return grupperingsId;
    }


    /**
      * Sets the value of the 'grupperingsId' field.
      * @param value The value of 'grupperingsId'.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder setGrupperingsId(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.grupperingsId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'grupperingsId' field has been set.
      * @return True if the 'grupperingsId' field has been set, false otherwise.
      */
    public boolean hasGrupperingsId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'grupperingsId' field.
      * @return This builder.
      */
    public no.nav.arbeidsgiver.notifikasjon.Done.Builder clearGrupperingsId() {
      grupperingsId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Done build() {
      try {
        Done record = new Done();
        record.tidspunkt = fieldSetFlags()[0] ? this.tidspunkt : (java.lang.Long) defaultValue(fields()[0]);
        if (mottakerBuilder != null) {
          try {
            record.mottaker = this.mottakerBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("mottaker"));
            throw e;
          }
        } else {
          record.mottaker = fieldSetFlags()[1] ? this.mottaker : (no.nav.arbeidsgiver.notifikasjon.Mottaker) defaultValue(fields()[1]);
        }
        record.grupperingsId = fieldSetFlags()[2] ? this.grupperingsId : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Done>
    WRITER$ = (org.apache.avro.io.DatumWriter<Done>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Done>
    READER$ = (org.apache.avro.io.DatumReader<Done>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










