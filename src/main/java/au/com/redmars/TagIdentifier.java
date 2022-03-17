package au.com.redmars;

import java.util.HashMap;
import java.util.Map;

public enum TagIdentifier {
    NewSubfileType(254),
    SubfileType(255),
    ImageWidth(256),
    ImageLength(257),
    BitsPerSample(258),
    Compression(259),
    PhotometricInterpretation(262),
    Make(271),
    Model(272),
    StripOffsets(273),
    Orientation(274),
    SamplesPerPixel(277),
    RowsPerStrip(278),
    StripByteCounts(279),
    PlanarConfiguration(284),
    Software(305),
    DateTime(306),
    SubIFDs(330),
    XMP(700),
    ExposureTime(33434),
    FNumber(33437),
    Exif_IFD(34665),
    ExposureProgram(34850),
    ISOSpeedRatings(34855),
    SensitivityType(34864),
    RecommendedExposureIndex(34866),
    ExifVersion(36864),
    DateTimeOriginal(36867),
    DateTimeDigitized(36868),
    OffsetTime(36880),
    ShutterSpeedValue(37377),
    ApertureValue(37378),
    ExposureBiasValue(37380),
    MaxApertureValue(37381),
    MeteringMode(37383),
    ImageNumber(37393),
    Flash(37385),
    FocalLength(37386),
    SubSecTimeOriginal(37521),
    SubSecTimeDigitized(37522),
    ColorSpace(40961),
    FocalPlaneXResolution(41486),
    FocalPlaneYResolution(41487),
    FocalPlaneResolutionUnit(41488),
    SensingMethod(41495),
    FileSource(41728),
    CustomRendered(41985),
    ExposureMode(41986),
    WhiteBalance(41987),
    DigitalZoomRatio(41988),
    SceneCaptureType(41990),
    BodySerialNumber(42033),
    LensSpecification(42034),
    LensModel(42036),
    LensSerialNumber(42037),
    DNGVersion(50706),
    DNGBackwardVersion(50707),
    UniqueCameraModel(50708),
    ColorMatrix1(50721),
    ColorMatrix2(50722),
    CameraCalibration1(50723),
    CameraCalibration2(50724),
    AnalogBalance(50727),
    AsShotNeutral(50728),
    BaselineExposure(50730),
    BaselineNoise(50731),
    BaselineSharpness(50732),
    LinearResponseLimit(50734),
    CameraSerialNumber(50735),
    LensInfo(50736),
    ShadowScale(50739),
    DNGPrivateData(50740),
    CalibrationIlluminant1(50778),
    CalibrationIlluminant2(50779),
    RawDataUniqueID(50781),
    OriginalRawFileName(50827),
    CameraCalibrationSignature(50931),
    ProfileCalibrationSignature(50932),
    ProfileName(50936),
    ProfileHueSatMapDims(50937),
    ProfileHueSatMapData1(50938),
    ProfileHueSatMapData2(50939),
    ProfileEmbedPolicy(50941),
    ProfileCopyright(50942),
    ForwardMatrix1(50964),
    ForwardMatrix2(50965),
    PreviewApplicationName(50966),
    PreviewApplicationVersion(50967),
    PreviewSettingsDigest(50969),
    PreviewColorSpace(50970),
    PreviewDateTime(50971),
    ProfileLookTableDims(50981),
    ProfileLookTableData(50982),
    NoiseProfile(51041),
    NewRawImageDigest(51111);

    private static final Map<Integer, TagIdentifier> BY_VALUE = new HashMap<>();
    

    public final Integer value;

    static {
        for (TagIdentifier e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }
    private TagIdentifier(Integer value) {
        this.value = value;
    }
    public static TagIdentifier valueOfTag(int value) {
        return BY_VALUE.get(value);
    }
}
