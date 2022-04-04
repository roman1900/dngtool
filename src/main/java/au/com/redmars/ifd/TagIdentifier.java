package au.com.redmars.ifd;

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
    Thresholding(270),
    Make(271),
    Model(272),
    StripOffsets(273),
    Orientation(274),
    SamplesPerPixel(277),
    RowsPerStrip(278),
    StripByteCounts(279),
    XResolution(282),
    YResolution(283),
    PlanarConfiguration(284),
    ResolutionUnit(296),
    Software(305),
    DateTime(306),
    Predictor(317),
    TileWidth(322),
    TileLength(323),
    TileOffsets(324),
    TileByteCounts(325),
    SubIFDs(330),
    YCbCrCoefficients(529),
    YCbCrSubSampling(530),
    YCbCrPositioning(531),
    ReferenceBlackWhite(532),
    XMP(700),
    CFARepeatPatternDim(33421),
    CFAPattern2(33422),
    ExposureTime(33434),
    FNumber(33437),
    Exif_IFD(34665),
    ExposureProgram(34850),
    GPSInfo(34853),
    ISOSpeedRatings(34855),
    SensitivityType(34864),
    RecommendedExposureIndex(34866),
    ExifVersion(36864),
    DateTimeOriginal(36867),
    DateTimeDigitized(36868),
    OffsetTime(36880),
    OffsetTimeOriginal(36881),
    OffsetTimeDigitized(36882),
    ShutterSpeedValue(37377),
    ApertureValue(37378),
    BrightnessValue(37379),
    ExposureBiasValue(37380),
    MaxApertureValue(37381),
    MeteringMode(37383),
    LightSource(37384),
    Flash(37385),
    FocalLength(37386),
    ImageNumber(37393),
    SubjectArea(37396),
    UserComment(37510),
    SubSecTimeOriginal(37521),
    SubSecTimeDigitized(37522),
    ColorSpace(40961),
    FocalPlaneXResolution(41486),
    FocalPlaneYResolution(41487),
    FocalPlaneResolutionUnit(41488),
    SensingMethod(41495),
    FileSource(41728),
    SceneType(41729),
    CFAPattern(41730),
    CustomRendered(41985),
    ExposureMode(41986),
    WhiteBalance(41987),
    DigitalZoomRatio(41988),
    FocalLengthIn35mmFilm(41989),
    SceneCaptureType(41990),
    GainControl(41991),
    Contrast(41992),
    Saturation(41993),
    Sharpness(41994),
    SubjectDistanceRange(41996),
    BodySerialNumber(42033),
    LensSpecification(42034),
    LensMake(42035),
    LensModel(42036),
    LensSerialNumber(42037),
    DNGVersion(50706),
    DNGBackwardVersion(50707),
    UniqueCameraModel(50708),
    LocalizedCameraModel(50709),
    CFAPlaneColor(50710),
    CFALayout(50711),
    LinearizationTable(50712),
    BlackLevelRepeatDim(50713),
    BlackLevel(50714),
    WhiteLevel(50717),
    DefaultScale(50718),
    DefaultCropOrigin(50719),
    DefaultCropSize(50720),
    ColorMatrix1(50721),
    ColorMatrix2(50722),
    CameraCalibration1(50723),
    CameraCalibration2(50724),
    AnalogBalance(50727),
    AsShotNeutral(50728),
    AsShotWhiteXY(50729),
    BaselineExposure(50730),
    BaselineNoise(50731),
    BaselineSharpness(50732),
    BayerGreenSplit(50733),
    LinearResponseLimit(50734),
    CameraSerialNumber(50735),
    DNGLensInfo(50736),
    ChromaBlurRadius(50737),
    AntiAliasStrength(50738),
    ShadowScale(50739),
    DNGPrivateData(50740),
    CalibrationIlluminant1(50778),
    CalibrationIlluminant2(50779),
    BestQualityScale(50780),
    RawDataUniqueID(50781),
    OriginalRawFileName(50827),
    ActiveArea(50829),
    ColorimetricReference(50879),
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
    OpcodeList2(51009),
    NoiseProfile(51041),
    OriginalDefaultFinalSize(51089),
    NewRawImageDigest(51111),
    CacheVersion(51114),
    DefaultUserCrop(51125);
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
